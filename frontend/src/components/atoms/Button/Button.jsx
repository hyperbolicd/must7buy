import React from 'react'
import styles from './Button.module.css'

export default function Button({ onClick, children, type = 'button', bubbles, variant = 'primary', size = 'm', disabled, shape}) {
  const className = `${styles.btn} ${styles[`btn-${variant}`]} ${styles[`btn-${size}`]} ${shape ? styles[`btn-${shape}`] : ''}`

  return (
    <button 
      onClick={e => {
        { !bubbles && e.stopPropagation() }
        onClick()
      }} 
      type={type}
      className={className}
      disabled={disabled}
    >
      {children}
    </button>
  )
}
