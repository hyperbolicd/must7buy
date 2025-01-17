import React from 'react'
import Header from '../components/Header/Header'
import Footer from '../components/Footer/Footer'
import { Outlet } from 'react-router-dom'
import Body from '../components/atoms/Body/Body'

export default function MainPage() {
  return (
    <div>
      <Header />
      <Body>
          <Outlet />
      </Body>
      <Footer />
    </div>
  )
}
