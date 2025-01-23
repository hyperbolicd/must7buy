import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import { debounce, getBase64FromUrl, getDataURLFromFile, getUrlFromBase64, imageAcceptType, validImageSize } from '../../utils/util'
import Button from '../../components/atoms/Button/Button'
import { createEmployee, getEmplooyeeById, updateEmployee } from '../../services/employeeService'

export default function SaveEmployeePage() {
  const params = useParams()
  const [employee, setEmployee] = useState({
    id: '',
    firstName: '',
    lastName: '',
    emailId: '',
    photo: '',
    hireDate: ''
  })
  const [photoUrl, setPhotoUrl] = useState(null)
  const navigate = useNavigate() 
  const [errors, setErrors] = useState({
    firstName: '',
    lastName: '',
    emailId: '',
  })

  useEffect(() => {
    window.scrollTo(0, 0)
    if(params.id === '_add') return
    (async () => {
      const result = await getEmplooyeeById(params.id)
      setEmployee(result);
    })()
    setPhotoUrl(getUrlFromBase64(employee.photo))
  }, [])

  function changeHandler(e) {
    const {name, value} = e.target
    switch(name) {
      case 'emailId':
        checkEmail(value)
      case 'firstName':
      case 'lastName':
        setEmployee({ ...employee, [name]: value })
        if (value.trim() !== '') {
          setErrors({ ...errors, [name]: '' })
        } else {
          validateField(name, value)
        }
        break
      case 'hireDate':
        setEmployee({ ...employee, [name]: value })
        break
      case 'photo':
        const file = e.target.files[0]
        if(!file) {
          break
        }

        console.log(file)
        if(imageAcceptType.includes(file.type) && validImageSize(file.size)) {
          (async (file) => {
            const dataUrl = await getDataURLFromFile(file)
            const dataBase64 = getBase64FromUrl(dataUrl)
            setEmployee({ ...employee, photo: dataBase64 })
            setPhotoUrl(dataUrl)
          })(file)
        } else {
          alert('僅接受 .png 及 .jpeg 且不大於 4MB')
        }
        break
      default:
        console.log('Does not meet the change state requirement.')
        break
    }
  } 

  const debouncedChangeHandler = debounce(changeHandler, 1000)

  function checkEmail(email) {
    (async () => {
      const res = checkEmail(email)
      const isValid = res.isValid
      console.log(isValid)
      if(!isValid) {
        setErrors({ ...errors, ['emailId']: 'Email 重複' });
      }
    })()
  }

  function validateField(name, value) {
    if ('firstName,lastName,emailId'.includes(name) && !value.trim()) {
      return "此欄位為必填";
    }
    if (name === "emailId" && !/\S+@\S+\.\S+/.test(value)) {
      return "請輸入有效的電子郵件";
    }
    return "";
  }

  function validateEmployee(employee) {
    Object.keys(employee).forEach((field) => {
      const error = validateField(field, employee[field])
      console.log(field +','+ employee[field] +','+ error)
      if(error) {
        console.log('Update:' + field +','+ employee[field] +','+ error)
        setErrors({ ...errors, [field]: error });
      }
    })
    console.log(errors)
    Object.keys(errors).forEach((field) => {
      if(errors[field].trim()) {
        alert('請填寫必要欄位')
        return false
      } 
    })
    return true
  }

  function handleUpdateEmployClick() {
    if(validateEmployee(employee)) {
      console.log('bbb')
      return
    }
    console.log('aaa')

    if(params.id === '_add') {
      (async () => {
        const result = await createEmployee(employee)
        if(result)
          navigate('../employees')
        else
          alert('建立失敗')
      })()
    } else {
      (async () => {
        const result = await updateEmployee(employee.id, employee)
        if(result)
          navigate('../employees')
        else
          alert('更新失敗')
      })()
    }
  }

  function handleCancelClick() {
    navigate('../employees')
  }

  return (
    <div>
      <Title>{params.id === '_add' ? '新增員工資料' : '修改員工資料'}</Title>
      <Content>
        <div className='card-body'>
          <form>
            <div className='mb-3'>
              <label>First Name: </label>
              <input placeholder='First Name' name='firstName' className='form-control'
                defaultValue={employee.firstName} onChange={debouncedChangeHandler} onInput={debouncedChangeHandler} onBlur={debouncedChangeHandler}/>
              { errors.firstName && <label style={{ color: 'red'}}> {errors.firstName} </label> }
            </div>
            <div className='mb-3'>
              <label>Last Name: </label>
              <input placeholder='Last Name' name='lastName' className='form-control'
                defaultValue={employee.lastName} onChange={(e) => debouncedChangeHandler(e)}/>
              { errors.lastName && <label style={{ color: 'red'}}> {errors.lastName} </label> }
            </div>
            <div className='mb-3'>
              <label htmlFor='label-emailId'>Email Address: </label>
              <input type='email' placeholder='Email Address' name='emailId' id='label-emailId' className='form-control'
                defaultValue={employee.emailId} onChange={(e) => debouncedChangeHandler(e)}/>
                { errors.emailId && <label style={{ color: 'red'}}> {errors.emailId} </label> }
            </div>
            <div className='mb-3'>
              <label>Hire Date: </label>
              <input type='date' placeholder='Hire Date' name='hireDate' id='label-hireDate' className='form-control'
                defaultValue={employee.hireDate} onChange={(e) => debouncedChangeHandler(e)}/>
            </div>
            <div className='mb-3'>
              <label>Photo: </label>
              <img src={photoUrl} />
            </div>
            <div className='mb-3'>
              <label>Update Photo: </label>
              <input type='file' name='photo' className='form-control'
                defaultValue={employee.photo} accept={imageAcceptType} onChange={(e) => debouncedChangeHandler(e)}/>
              <label style={{ color: 'red'}}>請上傳 .png 及 .jpeg 且檔案大小小於 4MB </label>
            </div>
            <div className='mb-3'>
            </div>
            <Button onClick={handleUpdateEmployClick}>{ params.id === '_add' ? 'Add' : 'Save'}</Button>
            <Button variant='warning' onClick={handleCancelClick}>Cancel</Button>
          </form>
        </div>
      </Content>
    </div>
  )
}
