import React from 'react'
import styles from './Card.module.css'
import { Link } from 'react-router-dom'

export default function Card({ title, link, imageUrl, description, children, variant = 'product', chilPosition = 'right'}) {
  return (
    <div className={styles[variant]}>
      <Link to={link}>
        <div>
          <img src={imageUrl}></img>
        </div>
        <div>
          <h4>{title}</h4>
        </div>
      { description &&
        <div>
          <span>{description}</span>
        </div>
      }
      </Link>
      { children &&
        <div className={styles[chilPosition]}>{children}</div>
      }
    </div>
  )
}
