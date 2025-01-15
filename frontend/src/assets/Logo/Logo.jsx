import React from 'react'
import styles from './Logo.module.css'

export default function Logo() {
  return (
    <div className={styles.logo}>
      <img src={require('./Must7BuySlim.png')} alt='Must7Buy' className={styles.img} />
    </div>
  )
}
