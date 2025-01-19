import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import EmployeeService from '../../services/EmployeeService'
import { debounce, getBase64FromUrl, getDataURLFromFile, getUrlFromBase64, imageAcceptType, validImageSize } from '../../utils/Util'
import Button from '../../components/atoms/Button/Button'

export default function SaveEmployeePage() {
  const params = useParams()
  const [employee, updateEmployee] = useState({
    id: params.id,
    firstName: '',
    lastName: '',
    emailId: '',
    photo: '',
    hireDate: '',
    photoUrl: ''
  })
  const navigate = useNavigate();  

  useEffect(() => {
    if(params.id === '_add') return
    
    EmployeeService.getEmplooyeeById(params.id)
      .then((defaultEmployee) => {
        if(defaultEmployee)
          updateEmployee(defaultEmployee)
      })
  }, [])

  function changeHandler(e) {
    if(e) {
      switch(e.target.name) {
        case 'firstName':
          updateEmployee({ ...employee, firstName: e.target.value })
          break
        case 'lastName':
          updateEmployee({ ...employee, lastName: e.target.value })
          break
        case 'emailId':
          updateEmployee({ ...employee, emailId: e.target.value })
          break
        case 'hireDate':
          updateEmployee({ ...employee, hireDate: e.target.value })
          break
        case 'photo':
          if(!e.target.files[0]) 
            break

          console.log(e.target.files[0])
          if(imageAcceptType.includes(e.target.files[0].type) && validImageSize(e.target.files[0].size)) {
            (async (file) => {
              const dataUrl = await getDataURLFromFile(file)
              const dataBase64 = getBase64FromUrl(dataUrl)
              updateEmployee({ ...employee, photo: dataBase64, photoUrl: dataUrl })
            })(e.target.files[0])
          } else {
            alert('僅接受 .png 及 .jpeg 且不大於 4MB')
          }
          break
        default:
          console.log('Does not meet the change state requirement.')
          break
      }
    }
  } 

  const debouncedChangeHandler = debounce(changeHandler, 1000)


  function handleUpdateEmployClick() {
    EmployeeService.updateEmployee(employee.id, employee)
    .then(updatedEmployee => {
      if(updatedEmployee)
        navigate('../employees')
      else
        alert('更新失敗')
    })
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
              <img src={getUrlFromBase64(employee.photo)} />
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
