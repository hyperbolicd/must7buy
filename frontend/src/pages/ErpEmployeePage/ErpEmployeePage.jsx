import React, { useEffect, useState } from 'react'
import Button from '../../components/atoms/Button/Button'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import { useNavigate } from 'react-router-dom'
import { deleteEmployee, getEmployees } from '../../services/employeeService'
import { useUser } from '../../contexts/UserContext'
import Table from '../../components/atoms/Table/Table'
import SubContent from '../../components/atoms/SubMenu/SubContent'

export default function ErpEmployeePage() {
  const [employees, setEmployees] = useState([])
  const navigate = useNavigate()
  const { user } = useUser()
  const theadMap = [
    { name: 'Username', attribute: 'username'},
    { name: 'DisplayName', attribute: 'displayName'},
    { name: 'Email', attribute: 'email'},
    { name: 'Photo', attribute: 'photo'},
    { name: 'Hire Date', attribute: 'hireDate'},
    { name: 'Role', attribute: 'role'},
    { name: 'Actions', attribute: 'button'},
  ]

  useEffect(() => {
    (async () => {
      const result = await getEmployees(user.token)
      setEmployees(result);
    })()
  }, [user])

  function handleCreateEmployeeOnClick() {
    navigate(`../employee/_add`)
  }

  function handleEditEmployeeOnClick(id) {
    navigate(`../employee/${id}`)
  }

  function handleDeleteEmployeeOnClick(id) {
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
        <SubContent position='right'>
          <Button onClick={handleCreateEmployeeOnClick} >
            新增員工
          </Button>
        </SubContent>
        <div>
          { // add buttons to data when render
            employees.forEach( employee => {
              employee.button = <div>
                <Button onClick={() => {handleEditEmployeeOnClick(employee.id)}}>
                  修改
                </Button>
                <Button variant='warning' onClick={() => {handleDeleteEmployeeOnClick(employee.id)}}>
                  刪除
                </Button>
              </div>
            })
          }
          <Table thead={theadMap} data={employees}></Table>
        </div>
      </Content>
    </div>
  )
}
