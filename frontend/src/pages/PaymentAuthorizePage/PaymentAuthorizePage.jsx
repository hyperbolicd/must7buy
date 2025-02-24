import React, { useEffect } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useUser } from '../../contexts/UserContext';
import { confirmPayment } from '../../services/paymentService';

export default function PaymentAuthorizePage() {
  const { user } = useUser()
  const [searchParams, setSearchParams] = useSearchParams()
  const navigate = useNavigate()

  useEffect(() => {
    (async () => {
      const result = await confirmPayment(user.token, searchParams.get("orderId"), searchParams.get("transactionId"));
      console.log(result);
      if(result.returnCode === "0000") {
        alert('付款成功')
      } else if(result.error || result.returnCode) {
        alert('付款失敗，請重新下單')
      }
      // navigate('/me')
    })()
  })

  return (
    <div><h1>付款中...</h1></div>
  )
}
