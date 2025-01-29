import React, { useState } from 'react'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import Button from '../../components/atoms/Button/Button'
import { debounce } from '../../utils/util'

export default function ErpLoginPage() {
  const [username, setUsername] = useState()
  const [password, setPassword] = useState()
  const [isHidden, setIsHidden] = useState(true)

  function changeHandler(e) {
    const {name, value} = e.target
    switch(name) {
      case 'username':
        setUsername(value)
        break
      case 'password':
        setPassword(value)
        break
      default:
        console.log('Does not meet the change state requirement.')
        break
    }
  } 

  const debouncedChangeHandler = debounce(changeHandler, 500)

  function handleShowPasswordClick() {
    setIsHidden(!isHidden)
  }

  function handleLoginClick() {
    
  }

  return (
    <div>
      <Title>後台登入</Title>
        <Content>
          <div className='card-body'>
            <form>
              <div className='mb-3'>
                <label>Username: </label>
                <input placeholder='Username' name='firstName' className='form-control'
                  defaultValue={username} onChange={debouncedChangeHandler}/>
              </div>
              <div className='mb-3'>
                <label>Password: </label>
                <input placeholder='Password' name='lastName' className='form-control' type={isHidden ? 'password':'text'}
                  defaultValue={password} onChange={(e) => debouncedChangeHandler(e)}/>
                <span onClick={handleShowPasswordClick}>{isHidden ? 'Show':'Hidden'}</span>
              </div>
              <Button onClick={handleLoginClick}>Login</Button>
            </form>
          </div>
        </Content>
    </div>
  )
}
