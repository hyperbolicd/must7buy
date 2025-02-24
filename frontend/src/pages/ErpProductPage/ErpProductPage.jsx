import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useUser } from '../../contexts/UserContext'
import { getProducts } from '../../services/productService'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import Button from '../../components/atoms/Button/Button'
import Table from '../../components/atoms/Table/Table'
import SubContent from '../../components/atoms/SubMenu/SubContent'

export default function ErpProductPage() {
  const [products, setProducts] = useState([])
  const navigate = useNavigate()
  const { user } = useUser()
  const theadMap = [
    { name: '分類', attribute: 'category'},
    { name: '商品名稱', attribute: 'name'},
    { name: '款式', attribute: 'style'},
    { name: '圖片', attribute: 'imageUrl'},
    { name: '價格', attribute: 'price'},
    { name: '庫存', attribute: 'stock'},
    { name: '封存', attribute: 'active'},
    { name: 'Actions', attribute: 'button'},
  ]

  useEffect(() => {
    (async () => {
      const result = await getProducts(user.token)
      if(!result.error) setProducts(result);
    })()
  }, [user])

  function handleCreateProductOnClick() {
    navigate('_bulk')
  }

  function handleEditProductOnClick() {
    navigate('_bulk_change')
  }

  function handleArchiveProductOnClick() {
    console.log("here: " + products)
    console.log(products)
    // api get archived products and update pruducts 
    // toggle back to 一般商品
  }

  return (
    <div>
      <Title>商品列表</Title>
        <Content>
          <SubContent position='right'>
            <Button onClick={handleCreateProductOnClick} >
              新增商品
            </Button>
            <Button onClick={handleEditProductOnClick} variant='secondary'>
              修改商品
            </Button>
            <Button onClick={handleArchiveProductOnClick} variant='warning'>
              封存商品
            </Button>
          </SubContent>
          <div>
            { // add buttons to data when render 
              products.forEach( product => {
                product.button = <div>
                  {/* <Button onClick={() => {handleEditProductOnClick(product.id)}}>
                    修改
                  </Button> */}
                  <Button variant='warning' onClick={() => {handleArchiveProductOnClick(product.id)}}>
                    封存
                  </Button>
                </div>
              })
            }
            <Table thead={theadMap} data={products}></Table>
          </div>
        </Content>
    </div>
  )
}
