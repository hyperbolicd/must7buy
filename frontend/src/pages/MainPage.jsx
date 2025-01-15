import React from 'react'
import Header from '../components/Header/Header'
import Footer from '../components/Footer/Footer'
import { Outlet } from 'react-router-dom'
import styles from './MainPage.module.css'

export default function MainPage() {
  return (
    <div>
        <Header />
            <div className={styles.container}>
                <Outlet />
            </div>
        <Footer />
    </div>
  )
}
