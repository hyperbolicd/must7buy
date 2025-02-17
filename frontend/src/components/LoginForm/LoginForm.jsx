import React, { useEffect, useState } from 'react'
import Button from '../atoms/Button/Button'
import { debounce, defaultDebounce } from '../../utils/util'
import { useUser } from '../../contexts/UserContext'
import { login as loginEmployee } from '../../services/employeeService'
import { Link, useNavigate } from 'react-router-dom'
import Form from '../atoms/Form/Form'
import { loginCustomer } from '../../services/customerService'
import Title from '../atoms/Title/Title'

export default function LoginForm() {
  const { user, isBackend, login } = useUser()
  const navigate = useNavigate()
  const [username, setUsername] = useState()
  const [password, setPassword] = useState()
  const [isHidden, setIsHidden] = useState(true)

  useEffect(() => {
    if(user) {
      navigate('../')
    }
  })

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

  function handleShowPasswordClick() {
    setIsHidden(!isHidden)
  }

  function handleLoginClick() {
    const tmpUser = {
      username: username,
      password: password
    };  // {(intermediate value)(intermediate value)} is not a function

    (async () => {
      let result
      if(isBackend) {
        result = await loginEmployee(tmpUser)
      } else {
        result = await loginCustomer(tmpUser)
      }
      
      if(!result.error) {
        const user = {
          username: username,
          token: result.token,
          expiresIn: result.expiresIn
        }
        login(user)
        if(!isBackend) {
          navigate(-1)
        }
      } else {
        alert('帳號或密碼不正確')
      }
    })()
  }

  return (
    <Form>
      <Title>{ isBackend ? '員工編號登入' : 'Email 登入' }</Title>
      <div>
        <input placeholder={ isBackend ? '員工編號, ex. E2025004' : 'Email, ex. abc@example.com' } name='username' className='form-control'
            defaultValue={username} onChange={debouncedChangeHandler}/>
      </div>
      <div>
        <input placeholder='Password' name='password' className='form-control' type={isHidden ? 'password':'text'}
            defaultValue={password} onChange={(e) => debouncedChangeHandler(e)}/>
        <div>
          <span onClick={handleShowPasswordClick}>  {isHidden ? 'Show':'Hide'} Password </span>
        </div>
      </div>
      
      <div className='center'>
        <Button onClick={handleLoginClick}>Login</Button>
      </div>
      <div className='center'>
        <label> { isBackend ? '忘記密碼請通知管理員重置' : <Link to='forget'>忘記密碼</Link>}</label>
      </div>
    </Form>
  )
}
