import React, { useEffect, useState } from 'react'
import { useCart } from '../../contexts/CartContext'
import { useUser } from '../../contexts/UserContext'
import { useNavigate, useParams } from 'react-router-dom'
import Content from '../../components/atoms/Content/Content'
import Title from '../../components/atoms/Title/Title'
import Table from '../../components/atoms/Table/Table'
import SubContent from '../../components/atoms/SubMenu/SubContent'
import Button from '../../components/atoms/Button/Button'
import ExpandableComponent from '../../components/atoms/Expandable/Expandable'
import { getOrderById, getProductByOrderId } from '../../services/orderService'
import { createPayments } from '../../services/paymentService'

export default function MyOrderPage() {
  const { cartItems, removeItem, updateQuantity, clearCart, totalPrice } = useCart()
  const { user } = useUser()
  const params = useParams()
  const navigate = useNavigate()
  const theadMap = [
    { name: '商品名稱', attribute: 'name'},
    { name: '款式', attribute: 'style'},
    { name: '圖片', attribute: 'imageUrl'},
    { name: '數量', attribute: 'quantity'},
    { name: '價格', attribute: 'price'},
  ]
  const [order, setOrder] = useState({
    'id': null,
    'totalPrice': 0,
    'status': '',
    'createdDate': '',
    'updatedDate': '',
    'paidAt': '',
    'shippedAt': '',
    'completedAt': ''
})
  const [orderItems, setOrderItems] = useState([])

  useEffect(() => {
    (async () => {
      const orderResult = await getOrderById(user.token, params.id);
      console.log(orderResult);
      setOrder(orderResult);
      const result = await getProductByOrderId(user.token, params.id);
      console.log(result);
      if(!result.error) {
        const items = []
        result.forEach(element => {
          items.push({
            id: element.product.id,
            name: element.product.name,
            style: element.product.style,
            imageUrl: element.product.imageUrl,
            quantity: element.quantity,
            price: element['unit_price'],
          })
        });
        setOrderItems(items);
      }
    })()
  }, [])

  function handleCheckoutClick() {
    
  }

  function handlePayClick() {
    (async () => {
      // const result = await createPayments(user.token, order, orderItems, 'LINE_PAY');
      const result = {
          "returnCode": "0000",
          "returnMessage": "Success.",
          "info": {
              "paymentUrl": {
                  "web": "https://sandbox-web-pay.line.me/web/payment/wait?transactionReserveId=UWRHS2tBaUtWb1I0RmhMQlVPNk5sem5FUXB2ZHZ0VnQ5U0dOQWRRY2kraUlxYlVtdXVDV1hIZmE0ZkZGaVJRTw",
                  "app": "line://pay/payment/UWRHS2tBaUtWb1I0RmhMQlVPNk5sem5FUXB2ZHZ0VnQ5U0dOQWRRY2kraUlxYlVtdXVDV1hIZmE0ZkZGaVJRTw"
              },
              "transactionId": 2025021702273625810,
              "paymentAccessToken": "772525889881"
          }
      }
      console.log(result);
      
      window.location.href = result.info.paymentUrl.web
    })()
  }

  return (
    <Content>
    { params.id === '_add' &&
    <>
      <Title>結帳明細</Title>
      <Table thead={theadMap} data={cartItems}></Table>
      <hr/>
      <Title>總價: ${totalPrice.toFixed(2)}</Title>
      <SubContent position='center'>
        <Button onClick={handleCheckoutClick}>確認結帳</Button>
      </SubContent>
      <SubContent position='center'>
        <ExpandableComponent title='注意事項'>
1. 訂單資訊確認
請確認您的訂單明細，包括商品名稱、數量、價格及總金額。
確保收件地址、配送方式和付款方式正確無誤。
訂單無法取消或修改，請再次檢查您的選擇。
<br/><br/>
2. 付款資訊
我們提供多種付款方式，包括信用卡、PayPal、線上轉帳等。
確保付款卡或帳戶的資訊正確無誤，以避免付款失敗。
信用卡付款可能會有額外的手續費，請確認支付金額。
<br/><br/>
3. 促銷優惠
若您有優惠碼，請在結帳時輸入以享有折扣。
優惠碼不能與其他促銷活動併用，請確認您的優惠條件。
部分優惠可能會有使用期限或適用限制，請詳細閱讀條款。
<br/><br/>
4. 配送選項
選擇您偏好的配送方式。標準配送一般需要 3-7 天，快速配送可在 1-2 天內送達。
部分地區可能無法提供某些配送選項，請確認您的地址是否符合配送條件。
若商品有不同配送時間，會以最長的配送時間為準。
<br/><br/>
5. 退換貨政策
請在收到商品後 7 天內聯絡我們辦理退換貨。
退貨商品必須保持原包裝、未使用過，並提供有效購買證明。
部分商品如內衣、開封的電子產品等，不適用於退貨。
<br/><br/>
6. 隱私與安全
我們承諾保護您的個人資料，所有交易均使用加密技術處理。
付款資訊不會與第三方共享，請放心進行結帳。
若有任何疑慮，請隨時聯絡我們的客服中心。
<br/><br/>
7. 訂單確認與發送
成功付款後，您將收到訂單確認信，並會附上訂單編號。
請記下訂單編號以便查詢及聯絡客服。
當商品發送後，我們會發送運送通知信，並提供追蹤編號。
<br/><br/>
8. 其他提醒
請確認所填寫的聯絡方式（如手機號碼及電子郵件）正確，以便我們能及時聯絡您。
若發現訂單有誤，請儘早聯絡客服，我們將協助處理。
        </ExpandableComponent>
      </SubContent>
      </>
    }
    { params.id != '_add' &&
    <>
      <Title>訂單明細</Title>
      <SubContent position='center'>
        <div>
          <p>訂單成立: {order.createdDate}</p>
          <p>訂單狀態: {order.status}</p>
          <p>訂單付款: {order.paidAt}</p>
          <p>訂單出貨: {order.shippedAt}</p>
          <p>訂單完成: {order.completedAt}</p>
          <p>更新日期: {order.updatedDate}</p>
        <Title>總價: ${order.totalPrice.toFixed(2)}</Title>
        { !order.paidAt &&
          <Button onClick={handlePayClick} variant='secondary'>訂單付款</Button>
        }
        <Button onClick={handleCheckoutClick} variant='warning'>取消訂單</Button>
        <hr />
        <Title>商品明細</Title>
        <Table thead={theadMap} data={orderItems}></Table>
        </div>
      </SubContent>
      </>
    }
    </Content>
  )
}
