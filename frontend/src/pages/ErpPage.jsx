import React, { Fragment } from 'react'
import { Link, Outlet } from 'react-router-dom'
import Header from '../components/Header/Header'
import Footer from '../components/Footer/Footer'
import styles from './ErpPage.module.css'

export default function ErpPage() {
  const navs = [
    { name: '商品管理', path: 'products', isOpen: true},
    { name: '用戶管理', path: 'customers', isOpen: true},
    { name: '訂單管理', path: 'orders', isOpen: false},
    { name: '統計報表', path: 'dashboard', isOpen: false},
    { name: '員工管理', path: 'employees', isOpen: true},
    { name: 'Home', path: '', isOpen: true},
    { name: '前台', path: '/', isOpen: true},
  ]

  return (
    <div>
      <nav>
        <div className={styles.function}>
        <span><b>後臺管理系統</b></span> 
        </div>
        { 
          navs.map(nav => {
            return (
              <div key={nav.path} className={styles.function}>
                <Link to={nav.path} className={!nav.isOpen && styles.disabled} target={ nav.name === '前台' && '_blank'}>{nav.name}</Link>
              </div>
            )
          })
        }
      </nav>
        <div className={styles.container}>
            <Outlet />
        </div>
      <Footer />
    </div>
  )
}
