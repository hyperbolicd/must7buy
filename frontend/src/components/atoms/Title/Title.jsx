import React from 'react'
import styles from './Title.module.css'

export default function Title({ children, textAlign = 'center', color = 'black' }) {
  return (
    <h2 className={styles.title}  style={{textAlign: textAlign, color: color}}>{children}</h2>
  )
}
