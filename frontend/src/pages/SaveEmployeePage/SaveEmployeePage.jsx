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
  const navigate = useNavigate();  

  useEffect(() => {
    window.scrollTo(0, 0)
    if(params.id === '_add') return
    (async () => {
      const result = await getEmplooyeeById(params.id)
      setEmployee(result);
    })()
  }, [params.id])

  useEffect(() => {
    if (employee.photo) {
      setPhotoUrl(getUrlFromBase64(employee.photo))
    }
  }, [employee.photo]);

  function changeHandler(e) {
    const {name, value} = e.target
    switch(name) {
      case 'firstName':
        setEmployee({ ...employee, firstName: value })
        break
      case 'lastName':
        setEmployee({ ...employee, lastName: value })
        break
      case 'emailId':
        setEmployee({ ...employee, emailId: value })
        break
      case 'hireDate':
        setEmployee({ ...employee, hireDate: value })
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


  function handleUpdateEmployClick() {
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
        const result = await setEmployee(employee.id, employee)
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
                defaultValue={employee.firstName} onChange={debouncedChangeHandler}/>
            </div>
            <div className='mb-3'>
              <label>Last Name: </label>
              <input placeholder='Last Name' name='lastName' className='form-control'
                defaultValue={employee.lastName} onChange={(e) => debouncedChangeHandler(e)}/>
            </div>
            <div className='mb-3'>
              <label htmlFor='label-emailId'>Email Address: </label>
              <input type='email' placeholder='Email Address' name='emailId' id='label-emailId' className='form-control'
                defaultValue={employee.emailId} onChange={(e) => debouncedChangeHandler(e)}/>
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
              <label>Update Photo: <label style={{ color: 'red'}}>僅接受 .png 及 .jpeg 且不大於 4MB </label></label>
              <input type='file' name='photo' className='form-control'
                defaultValue={employee.photo} accept={imageAcceptType} onChange={(e) => debouncedChangeHandler(e)}/>
            </div>
            <div className='mb-3'>
            </div>
            <Button onClick={handleUpdateEmployClick}>Save</Button>
            <Button variant='warning' onClick={handleCancelClick}>Cancel</Button>
          </form>
        </div>
      </Content>
    </div>
  )
}
