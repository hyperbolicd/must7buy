import React, { useEffect, useState } from 'react'
import styles from './ErpEmployeePage.module.css'
import { getUrlFromBase64 } from '../../utils/util'
import Button from '../../components/atoms/Button/Button'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import { useNavigate, useParams } from 'react-router-dom'
import { deleteEmployee, getEmployees } from '../../services/employeeService'
import { useUser } from '../../contexts/UserContext'

export default function ErpEmployeePage() {
  const [employees, setEmployees] = useState([])
  const navigate = useNavigate()
  const { user } = useUser()

  useEffect(() => {
    (async () => {
      const result = await getEmployees(user.token)
      setEmployees(result);
    })()
  }, [])

  function handleCreateEmployOnClick() {
    navigate(`../employee/_add`)
  }

  function handleEditEmployOnClick(id) {
    navigate(`../employee/${id}`)
  }

  function handleDeleteEmployOnClick(id) {
    const message = `刪除員工可能導致有相關欄位資料缺損
    請確認是否刪除員工 [${employees.find((employee) => employee.id === id).displayName}]`
    const result = window.confirm(message);
    if (result) {
      (async () => {
        const result = await deleteEmployee(user.token, id)
        if(result)
          setEmployees(employees.filter( employee => employee.id != id ))
        else
          alert('刪除失敗')
      })()
    }
  }

  return (
    <div>
      <Title>員工列表</Title>
      <Content>
        <div className={styles.buttonDiv}>
          <Button onClick={handleCreateEmployOnClick} >
            新增員工
          </Button>
        </div>
        <div>
          <table className={styles.table}>
            <thead>
              <tr>
                <th> Username</th>
                <th> DisplayName</th>
                <th> Email</th>
                <th> Photo</th>
                <th> Hire Date</th>
                <th> Role</th>
                <th> Actions</th>
              </tr>
            </thead>
            <tbody>
              { 
                employees.map( employee =>
                    <tr key={employee.id}>
                      <td> {employee.username}</td>
                      <td> {employee.displayName}</td>
                      <td> {employee.email}</td>
                      <td> 
                        <img src={getUrlFromBase64(employee.photo)} />
                      </td>
                      <td> {employee.hireDate}</td>
                      <td> {employee.role}</td>
                      <td>
                        <Button onClick={() => {handleEditEmployOnClick(employee.id)}}>
                          修改
                        </Button>
                        <Button variant='warning' onClick={() => {handleDeleteEmployOnClick(employee.id)}}>
                          刪除
                        </Button>
                      </td>
                    </tr>
                )
              }
            </tbody>
          </table>
        </div>
      </Content>
    </div>
  )
}
