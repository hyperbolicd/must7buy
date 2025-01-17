import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import EmployeeService from '../../services/EmployeeService'
import { getUrlFromBase64 } from '../../utils/Util'
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
  })
  const navigate = useNavigate();

  useEffect(() => {
      EmployeeService.getEmplooyeeById(params.id)
      .then((fetchedData) => {
        console.log(fetchedData)
        if(fetchedData)
          updateEmployee(fetchedData)
      })
    }, [])

  function changeHandler() {

  } 

  function handleUpdateEmployClick() {

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
                value={employee.firstName} onChange={changeHandler} />
            </div>
            <div className='mb-3'>
              <label>Last Name: </label>
              <input placeholder='Last Name' name='lastName' className='form-control'
                value={employee.lastName} onChange={changeHandler} />
            </div>
            <div className='mb-3'>
              <label>Email Address: </label>
              <input type='email' placeholder='Email Address' name='emailId' id='label-emailId' className='form-control'
                value={employee.emailId} onChange={changeHandler} />
            </div>
            <div className='mb-3'>
              <label>Hire Date: </label>
              <input type='date' placeholder='Hire Date' name='hireDate' id='label-hireDate' className='form-control'
                value={employee.hireDate} onChange={changeHandler} />
            </div>
            <div className='mb-3'>
              <label>Photo: </label>
              <img src={getUrlFromBase64(employee.photo)} />
            </div>
            <div className='mb-3'>
              <label>Update Photo: </label>
              <input type='file' name='photo' id='label-photo' className='form-control'
                value={employee.photo} onChange={changeHandler} />
            </div>
            <Button onClick={handleUpdateEmployClick}>Save</Button>
            <Button variant='warning' onClick={handleCancelClick}>Cancel</Button>
          </form>
        </div>
      </Content>
    </div>
  )
}
