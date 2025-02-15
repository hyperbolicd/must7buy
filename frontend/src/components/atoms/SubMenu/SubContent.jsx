import React from 'react'
import styles from './SubContent.module.css'

export default function SubContent({ children, position, gap}) {
  const className = `${styles[position]} ${styles[`gap-${gap}`]}`

  return (
    <div className={className}>
        { // 1 個 children component 時 length 為 undefined
            children != null && (children.length === undefined) && <div className={styles.inner} >{children}</div>
        }
        {
            children != null && children.length > 1 && children.map(child => (<div className={styles.inner} >{child}</div>))
        }
    </div>
  )
}
