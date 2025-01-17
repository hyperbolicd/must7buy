import React from 'react'
import { Outlet } from 'react-router-dom'
import Footer from '../components/Footer/Footer'
import Body from '../components/atoms/Body/Body'
import ErpHeader from '../components/ErpHeader/ErpHeader'

export default function ErpPage() {
  
  return (
    <div>
      <ErpHeader />
      <Body>
          <Outlet />
      </Body>
      <Footer />
    </div>
  )
}
