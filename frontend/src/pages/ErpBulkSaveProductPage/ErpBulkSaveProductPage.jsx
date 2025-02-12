import React, { useState } from 'react'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import { useNavigate, useParams } from 'react-router-dom'
import Table from '../../components/atoms/Table/Table'
import SubContent from '../../components/atoms/SubMenu/SubContent'
import Button from '../../components/atoms/Button/Button'
import { defaultDebounce, getDataURLFromFile, imageAcceptType, validImageSize } from '../../utils/util'
import { createProduct, uploadProductImage } from '../../services/productService'
import { useUser } from '../../contexts/UserContext'

export default function ErpBulkSaveProductPage() {
  const [products, setProducts] = useState([])
  const [serial, setSerial] = useState(1)
  const params = useParams()
  const { user } = useUser()
  const navigate = useNavigate()
  const isCreate = params.action === '_bulk'
  const theadMap = [
    { name: '分類', attribute: 'category'},
    { name: '商品名稱', attribute: 'name'},
    { name: '款式', attribute: 'style'},
    { name: '圖片', attribute: 'image'}, // image -> file
    { name: '價格', attribute: 'price'},
    { name: '庫存', attribute: 'stock'},
    { name: 'Actions', attribute: 'button'},
    { name: 'Result', attribute: 'result'},
  ]

  function handleMinusClick(tmpId) {
    setProducts(products.filter( product => product.tmpId != tmpId))
  }

  function handleAddClick() {
    if(products.length >= 5) {
      alert('一次最多五筆')
      return
    }
    console.log(products)
    setSerial(serial + 1)

    setProducts([...products, {
      tmpId: serial,
      category: '',
      name: '',
      style: '',
      image: '',
      price: 0,
      stock: 1
    }])
  }

  function changeHandler(e) {
    const {name, value, files} = e.target
    const updatedProducts = structuredClone(products)
    console.log(updatedProducts)
    
    if(name === 'image') {
      if(!files[0]) return;
      const file = files[0]
      console.log(file)

      const tr = e.target.parentElement.parentElement.parentElement
      const trId = tr.getAttribute('name')
      const targetProduct = updatedProducts.find(product => 
        `${product.tmpId}` === trId
      )
      
      if(imageAcceptType.includes(file.type) && validImageSize(file.size)) {
        (async (file) => {
          const dataUrl = await getDataURLFromFile(file)
          console.log(dataUrl)
          targetProduct['directUrl'] = dataUrl
          targetProduct['image'] = file
          setProducts(updatedProducts)
        })(file)
      } else {
        alert('僅接受 .png 及 .jpeg 且不大於 4MB')
      }
    } else {
      const tr = e.target.parentElement.parentElement
      const trId = tr.getAttribute('name')
      const targetProduct = updatedProducts.find(product => 
        `${product.tmpId}` === trId
      )
      console.log(tr)

      targetProduct[name] = value
      setProducts(updatedProducts)
    }
    console.log(products)
  } 

  const debouncedChangeHandler = defaultDebounce(changeHandler)

  function productsToColumns() {
    let columns = []
    products.forEach(product => {
      columns.push({
        tmpId: product.tmpId,
        category: <input name='category' onChange={(e) => debouncedChangeHandler(e)} />,
        name: <input name='name' onChange={(e) => debouncedChangeHandler(e)} />,
        style: <input name='style' onChange={(e) => debouncedChangeHandler(e)} />,
        image: <div><img src={product.directUrl} /><input type='file' name='image' onChange={(e) => debouncedChangeHandler(e)} /></div>,
        price: <input type='number' defaultValue={product.price} name='price' onChange={(e) => debouncedChangeHandler(e)} />,
        stock: <input type='number' defaultValue={product.stock} name='stock' onChange={(e) => debouncedChangeHandler(e)} />,
        button: <Button onClick={() => handleMinusClick(product.tmpId)} variant='warning'>-</Button>,
        result: product.result
      })
    })
    return columns
  }

  function handleCreateClick() {
    const updatedProducts = structuredClone(products)

    products.forEach(product => {
      if(product.result != '已上傳') {
        const targetProduct = updatedProducts.find(innerProduct => 
          innerProduct.tmpId === product.tmpId
        )
        let imageUploadSuccess = true;

        (async () => {
          if(product.image) {
            console.log(product.image)
            const result = await uploadProductImage(user.token, product.image)
            console.log('Upload result: ')
            console.log(result)
            
            if(!result.error)
              product['imageUrl'] = result.photoPath
            else
              imageUploadSuccess = false
          }

          if(!imageUploadSuccess) {
            targetProduct['result'] = 'FAIL' 
          } else {
              const result = await createProduct(user.token, product)
              console.log('Add product result: ')
              console.log(result)
      
              if(!result.error)
                targetProduct['result'] = '已上傳'
              else {
                console.error(result.error)
                targetProduct['result'] = `FAIL:${JSON.stringify(result.error)}` 
              }
                
          }
          setProducts(updatedProducts)
          console.log(product)
        })();
      }
    })

    const allSuccess = products.every(product => product.result === '已上傳');
    if(allSuccess) {
      alert('已全數上傳')
      navigate('../products')
    } 
  }

  return (
    <div>
      <Title> { isCreate ? '新增商品' : '修改商品' } </Title>
      <Content>
        <SubContent position='right'>
          <Button onClick={handleAddClick} variant='secondary'>+</Button>
          <Button onClick={handleCreateClick}>新增</Button>
        </SubContent>
        <Table thead={theadMap} data={productsToColumns()}></Table>
      </Content>
    </div>
  )
}
