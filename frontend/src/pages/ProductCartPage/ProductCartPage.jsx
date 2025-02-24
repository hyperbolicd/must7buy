import React, { useEffect, useState } from 'react'
import Content from '../../components/atoms/Content/Content'
import Title from '../../components/atoms/Title/Title'
import { useCart } from '../../contexts/CartContext'
import Button from '../../components/atoms/Button/Button'
import Table from '../../components/atoms/Table/Table'
import SubContent from '../../components/atoms/SubMenu/SubContent'
import { useUser } from '../../contexts/UserContext'
import { useNavigate } from 'react-router-dom'

export default function ProductCartPage() {
  const { cartItems, removeItem, updateQuantity, clearCart, totalPrice } = useCart()
  const [tdata, setTdata] = useState([])
  const theadMap = [
    { name: '商品名稱', attribute: 'name'},
    { name: '款式', attribute: 'style'},
    { name: '圖片', attribute: 'imageUrl'},
    { name: '數量', attribute: 'quantity'},
    { name: '價格', attribute: 'price'},
    { name: 'Actions', attribute: 'button'},
  ]
  const { user } = useUser()
  const navigate = useNavigate()

  useEffect(() => {
    const temp = structuredClone(cartItems)
    setTdata(temp)
  }, [cartItems])

  function handleClearCartClick() {
    if(window.confirm('確認清空購物車?')) {
      clearCart()
    }
  }

  function handleCheckoutClick() {
    if(cartItems.length > 0 && window.confirm('確認結帳?')) {
      if(!user) {
        alert('請先登入')
      }
      navigate('/me/orders/_add')
    }
  }

  return (
    <Content>
      <Title>購物車</Title>
      { // add buttons to data when render 
        tdata.forEach( item => {
          item.button = <div key={item.id}>
            <Button onClick={() => updateQuantity(item.id, item.quantity + 1)} disabled={item.quantity >= item.stock}>+</Button>
            <Button onClick={() => updateQuantity(item.id, item.quantity - 1)} disabled={item.quantity === 1} variant='secondary'>-</Button>
            <Button onClick={() => removeItem(item.id)} variant='warning'>移除</Button>
          </div>
        })
      }
      <Table thead={theadMap} data={tdata}></Table>
      <Title>總價: ${totalPrice.toFixed(2)}</Title>
      <SubContent position='center'>
        <Button onClick={handleClearCartClick} variant='warning'>清空購物車</Button>
        <Button onClick={handleCheckoutClick}>結帳</Button>
      </SubContent>
    </Content>
  )
}
