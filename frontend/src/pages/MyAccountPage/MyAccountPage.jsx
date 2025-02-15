import React, { useEffect, useState } from 'react'
import { useUser } from '../../contexts/UserContext'
import { getCustomerByUsername } from '../../services/customerService'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import SubContent from '../../components/atoms/SubMenu/SubContent'
import Form from '../../components/atoms/Form/Form'
import Table from '../../components/atoms/Table/Table'
import Button from '../../components/atoms/Button/Button'
import { useNavigate } from 'react-router-dom'

export default function MyAccountPage() {
  const { user, isBackend, login, logout } = useUser()
  const navigate = useNavigate()
  const [me, setMe] = useState({
    displayName: '',
    username: '',
    email: '',
    address: '',
    cart: '',
  })
  const [order, setOrder] = useState()
  const theadMap = [ // 注文	日付	支払状況	発送状況	合計
    { name: '訂單編號', attribute: 'id'},
    { name: '訂單日期', attribute: 'createdDate'},
    { name: '付款日期', attribute: 'updatedDate'},
    { name: '總金額', attribute: 'totalPrice'},
    { name: '訂單狀態', attribute: 'status'},
  ]

  useEffect(() => {
    window.scrollTo(0, 0);

    (async () => {
      const result = await getCustomerByUsername(user.token, user.username);
      console.log(result);
      // latest 3 orders
      setMe(result);
    })()
  }, [])

  function handleLogoutClick() {
    logout(user)
    navigate('/')
  }

  function handleUpdateClick() {
    alert('TO DO')
  }

  function handleOrderClick() {
    alert('TO DO')
  }

  return (
    <div>
      <Title>Hi! {me.displayName} </Title>
      <Content>
          <SubContent position='center' gap='s'>
            <Form>
              <Title>個人資訊</Title>
              <div>
                <label>名稱</label>
                <div>{me.displayName}</div>
              </div>
              <div>
                <label>Email</label>
                <div>{me.email}</div>
              </div>
              <div>
                <label>出貨地址</label>
                <div>{me.address}</div>
              </div>
              <div className='center'>
                <Button onClick={handleUpdateClick} variant='secondary'>更新</Button>
                <Button onClick={handleLogoutClick} variant='warning'>登出</Button>
              </div>
            </Form>
            <SubContent>
              <Title>近期訂單<Button onClick={handleOrderClick} variant='transparnet'>歷史訂單</Button></Title>
              <Table thead={theadMap}></Table>
            </SubContent>
          </SubContent>
      </Content>
    </div>
  )
}
