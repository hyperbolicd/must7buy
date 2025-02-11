import React, { useState } from 'react'
import { env } from '../../../configs/config'
import { getThumbnailUrl } from '../../../utils/util'
import styles from './Table.module.css'

export default function Table({ onClick, type = 'table', thead, data }) {
  const [sortBy, setSortBy] = useState()

  function handleTheadOnClick() {
    // sort by click and reverse when click the same
    alert("Default onClick")
  }

  return (
    <table className={styles.table}>
      <thead onClick={e => {
          { onClick && onClick() }
          { !onClick && handleTheadOnClick() }
      }}>
        <tr>
          {
            thead.map( th => {
              return(<th key={th.attribute}> {th.name} </th>)
            })
          }
        </tr>
      </thead>
      <tbody>
        { 
          data.map(row => (
            <tr key={row.id}>
              {
                thead.map( th => {
                  if(th.attribute === 'imageUrl')
                    return (<th key={th.attribute}> <img src={`${env.MEDIA_SOURCE_URL}/${getThumbnailUrl(row[th.attribute])}`} /> </th>)
                  else
                    return (<td key={th.attribute}> {row[th.attribute]} </td>)
                })
              }
            </tr>
          ))
        }
      </tbody>
  </table>
  )
}
