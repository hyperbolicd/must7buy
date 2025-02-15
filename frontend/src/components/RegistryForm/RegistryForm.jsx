import React, { useState } from 'react'
import Form from '../atoms/Form/Form'
import Button from '../atoms/Button/Button'
import { defaultDebounce, isValidEmail } from '../../utils/util'
import { useNavigate } from 'react-router-dom'
import { createCustomer } from '../../services/customerService'
import Title from '../atoms/Title/Title'

export default function RegistryForm() {
  const [isRegistry, setIsRegistry] = useState(false)
  const [username, setUsername] = useState()
  const [password, setPassword] = useState()
  const [isHidden, setIsHidden] = useState(true)
  const navigate = useNavigate()

  function handleRegistryClick() {
    if(isRegistry) {
      registry() 
    } else {
      setIsRegistry(true)
    }
  }

  function handleShowPasswordClick() {
    setIsHidden(!isHidden)
  }

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
  
  const debouncedChangeHandler = defaultDebounce(changeHandler)

  function registry() {
    if(!isValidEmail(username)) {
      alert('Email 格式不符')
      return
    }

    const tmpUser = {
      username: username,
      displayName: username,
      email: username,
      password: password
    };  // {(intermediate value)(intermediate value)} is not a function

    (async () => {
      console.log(tmpUser)
      let result = await createCustomer(tmpUser)

      if(!result.error) {
        alert('申請成功，請重新登入')
        navigate(0)
      } else {
        console.error(JSON.stringify(result.error))
        alert(JSON.stringify(result.error))
      }
    })()
  }

  return (
    <Form>
      <Title>註冊帳號</Title>
      <div className='center'>
        <label> 沒有帳號請先進行註冊 </label>
      </div>
      {
        isRegistry &&
        <div>
          <input placeholder='Email, ex. abc@example.com' name='username' className='form-control'
              defaultValue={username} onChange={debouncedChangeHandler}/>
        </div>
      }
      {
        isRegistry &&
        <div>
          <input placeholder='Password' name='password' className='form-control' type={isHidden ? 'password':'text'}
              defaultValue={password} onChange={(e) => debouncedChangeHandler(e)}/>
          <div>
            <span onClick={handleShowPasswordClick}>  {isHidden ? 'Show':'Hide'} Password </span>
          </div>
        </div>
      }
      <div className='center'>
        <Button onClick={handleRegistryClick}>{ isRegistry ? 'Send' : 'Registry' }</Button>
      </div>
    </Form>
  )
}
