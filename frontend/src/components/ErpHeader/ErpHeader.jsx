import React from 'react'
import styles from './ErpHeader.module.css'
import { Link } from 'react-router-dom'

export default function ErpHeader() {
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
    <header>
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
    </header>    
  )
}
