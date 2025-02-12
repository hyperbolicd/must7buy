import React, { useState } from 'react'
import { env } from '../../../configs/config'
import { getThumbnailUrl, getUrlFromBase64 } from '../../../utils/util'
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
            <tr key={row.id} name={ row.tmpId ? row.tmpId : row.id}>
              {
                thead.map( th => {
                  if(th.attribute === 'imageUrl' && row[th.attribute]) // aws
                    return (<th key={th.attribute}> <img src={`${env.MEDIA_SOURCE_URL}/${getThumbnailUrl(row[th.attribute])}`} /> </th>)
                  else if(th.attribute === 'photo' && row[th.attribute]) // blob
                    return (<th key={th.attribute}> <img src={getUrlFromBase64(row[th.attribute])} /> </th>)
                  else if(th.attribute === 'directUrl' && row[th.attribute]) // preview
                    return (<th key={th.attribute}> <img src={row[th.attribute]} /> </th>)
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
