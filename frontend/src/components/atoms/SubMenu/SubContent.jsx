import React from 'react'
import styles from './SubContent.module.css'

export default function SubContent({ children, position }) {
  return (
    <div className={styles[position]}>
        { // 1 個 children 時 length 為 undefined
            !(children != null && children.length > 1) && <div className={styles.inner} >{children}</div>
        }
        {
            children != null && children.length > 1 && children.map(child => (<div className={styles.inner} >{child}</div>))
        }
    </div>
  )
}
