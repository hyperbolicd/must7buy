import React, { useEffect, useState } from 'react'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import SubContent from '../../components/atoms/SubMenu/SubContent'
import Form from '../../components/atoms/Form/Form'
import { useSearchParams } from 'react-router-dom'
import { searchProducts } from '../../services/productService'
import Card from '../../components/atoms/Card/Card'
import { env } from '../../configs/config'
import { getThumbnailUrl } from '../../utils/util'
import Button from '../../components/atoms/Button/Button'
import CartIcon from '../../assets/CartIcon'
import { useCart } from '../../contexts/CartContext'

export default function ProductSearchPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const [products, setProducts] = useState([]);
  const categories = [
    { name: '衣服', keywords: 'cloths'},
    { name: '帽子', keywords: 'hats'},
    { name: '褲子', keywords: 'pants'},
    { name: '大衣', keywords: 'coats'},
    { name: '洋裝', keywords: 'dress'},
    { name: '玩偶', keywords: 'dolls'},
    { name: '吊飾', keywords: 'keyholders'},
    { name: '立牌', keywords: 'acrylics'},
    { name: '相片', keywords: 'pictures'},
    { name: '聲音', keywords: 'voices'},
  ]
  const idols = [
    { name: 'Red', keywords: 'red'},
    { name: 'Yellow', keywords: 'yellow'},
    { name: 'Green', keywords: 'green'},
    { name: 'Black', keywords: 'black'},
    { name: 'Pink', keywords: 'pink'},
  ]
  const { cartItems, addItem, updateQuantity } = useCart();
  
  useEffect(() => {
    (async () => {
      const result = await searchProducts()
      console.log(result)
      if(!result.error) setProducts(result);
      console.log(products)
    })()
  }, [])

  function handleAddCartClick(id) {
    const targetProduct = products.find( product => 
      product.id === id
    )
    console.log(targetProduct?.name + '-' + targetProduct?.style)

    console.log(cartItems)
    const existingItem = cartItems.find((item) => item.id === id)
    if (existingItem) {
      updateQuantity(existingItem.id, existingItem.quantity + 1);
    } else {
      const newItem = {
        id: targetProduct.id,
        name: targetProduct.name,
        style: targetProduct.style,
        stock: targetProduct.stock,
        quantity: 1,
        price: targetProduct.price,
        imageUrl: targetProduct.imageUrl,
      }
      addItem(newItem);
    }
    
  }

  return (
    <div>
      <Content>
        <SubContent position='center' gap='s' inner='inline-block'>
          {/* <div>
            <Title>篩選條件</Title>
            <hr/>
            <fieldset>
              <legend>選擇分類</legend>
              {
                categories.map(category => (
                  <div key={category.keywords}>
                    <input type="checkbox" id={category.keywords} name='category' value={category.keywords} />
                    <label for={category.keywords}>{category.name}</label>
                  </div>
                ))
              }
            </fieldset>
            <fieldset>
              <legend>選擇偶像</legend>
              {
                idols.map(idol => (
                  <div key={idol.keywords}>
                    <input type="checkbox" id={idol.keywords} name='idol' value={idol.keywords} />
                    <label for={idol.keywords}>{idol.name}</label>
                  </div>
                ))
              }
            </fieldset>
            <hr/>
            <Title>Others</Title>
          </div> */}
          <div style={{ minWidth: '400px', maxWidth: '800px'}}>
            <Title>全商品</Title>
            {/* <div>{searchParams.get('category')}__{searchParams.get('name')}</div> */}
            <SubContent position='left' gap='s' maxWidth='400px'>
              {
                products.map(product =>
                  <Card title={`${product.name} ${product.style}`} link={`/products/${product.id}`} imageUrl={`${env.MEDIA_SOURCE_URL}/${getThumbnailUrl(product.imageUrl)}`} >
                    <>
                      <div style={{ display: 'flex', alignContent: 'center', alignItems: 'center', justifyContent: 'flex-end'}}>
                        <span style={{ color: 'red'}}>${product.price}&nbsp;&nbsp;</span>
                        {/* <input type='number' min='1' max={product.stock} name='quantity' defaultValue='1' style={{ width: '30px'}}/> */}
                        <Button variant='warning' size='s' onClick={() => handleAddCartClick(product.id)}><CartIcon/></Button>
                      </div>
                    </>
                  </Card>
                )
              }
            </SubContent>
          </div>
        </SubContent>
      </Content>
    </div>
  )
}
