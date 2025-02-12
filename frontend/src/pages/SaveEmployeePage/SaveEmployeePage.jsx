import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import { debounce, getBase64FromUrl, getDataURLFromFile, getUrlFromBase64, imageAcceptType, validImageSize } from '../../utils/util'
import Button from '../../components/atoms/Button/Button'
import { createEmployee, getEmplooyeeById, updateEmployee } from '../../services/employeeService'
import { useUser } from '../../contexts/UserContext'
import SubContent from '../../components/atoms/SubMenu/SubContent'

export default function SaveEmployeePage() {
  const params = useParams()
  const [employee, setEmployee] = useState({
    id: '',
    username: '',
    password: '',
    displayName: '',
    email: '',
    address: '',
    photo: '',
    hireDate: '',
    role: ''
  })
  const [photoUrl, setPhotoUrl] = useState(null)
  const navigate = useNavigate();  
  const { user } = useUser()
  const isCreate = params.id === '_add'

  useEffect(() => {
    window.scrollTo(0, 0)
    if(isCreate) return
    (async () => {
      const result = await getEmplooyeeById(user.token, params.id)
      setEmployee(result);
    })()
  }, [params.id])

  useEffect(() => {
    if (employee.photo) {
      setPhotoUrl(getUrlFromBase64(employee.photo))
    }
  }, [employee.photo]);

  function changeHandler(e) {
    const {name, value, files} = e.target
    const updatedEmployee = { ...employee }

    if(name === 'photo' && files[0]) {
      const file = files[0]
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
    } else {
      updatedEmployee[name] = value
    }
    setEmployee(updatedEmployee)
  } 

  const debouncedChangeHandler = debounce(changeHandler, 1000)


  function handleUpdateEmployClick() {
    if(isCreate) {
      (async () => {
        const result = await createEmployee(user.token, employee)
        if(result)
          navigate('../employees')
        else
          alert('建立失敗')
      })()
    } else {
      (async () => {
        const result = await updateEmployee(user.token, employee.id, employee)
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
      <Title>{isCreate ? '新增員工資料' : '修改員工資料'}</Title>
      <Content>
        <SubContent position='center'>
          <form>
            { !(isCreate) &&
              <div>
                <label>Username: </label>
                <span>{employee.username}</span>
              </div>
            }
            <div>
              <label>Display Name: </label>
              <input placeholder='Display Name' name='displayName' 
                defaultValue={employee.displayName} onChange={(e) => debouncedChangeHandler(e)}/>
            </div>
            { isCreate &&
              <div>
                <label>Password: </label>
                <input placeholder='Password' name='password' 
                  defaultValue={employee.password} onChange={debouncedChangeHandler}/>
              </div>
            }
            <div>
              <label htmlFor='label-email'>Email: </label>
              <input type='email' placeholder='Email' name='email' id='label-email' 
                defaultValue={employee.email} onChange={(e) => debouncedChangeHandler(e)}/>
            </div>
            <div>
              <label>Address: </label>
              <input placeholder='Address' name='address' 
                defaultValue={employee.address} onChange={(e) => debouncedChangeHandler(e)}/>
            </div>
            <div>
              <label>Hire Date: </label>
              <input type='date' placeholder='Hire Date' name='hireDate' id='label-hireDate' 
                defaultValue={employee.hireDate} onChange={(e) => debouncedChangeHandler(e)}/>
            </div>
            { !(isCreate) &&
              <div>
                <label>Role: </label>
                <select name='role' onChange={(e) => debouncedChangeHandler(e)}>
                  <option selected={ employee.role === 'EMPLOYEE' ? 'selected' : ''}> EMPLOYEE </option>
                  <option selected={ employee.role === 'ADMIN' ? 'selected' : ''}> ADMIN </option>
                </select>
              </div>
            } 
            <div>
              <label>Photo: </label>
              <img src={photoUrl} />
            </div>
            <div>
              <label>Update Photo: <label style={{ color: 'red'}}>僅接受 .png 及 .jpeg 且不大於 4MB </label></label>
              <input type='file' name='photo' 
                defaultValue={employee.photo} accept={imageAcceptType} onChange={(e) => debouncedChangeHandler(e)}/>
            </div>
            <div>
            </div>
            <Button onClick={handleUpdateEmployClick}>Save</Button>
            <Button variant='warning' onClick={handleCancelClick}>Cancel</Button>
          </form>
        </SubContent>
      </Content>
    </div>
  )
}
