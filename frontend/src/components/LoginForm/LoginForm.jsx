import React, { useEffect, useState } from 'react'
import Button from '../atoms/Button/Button'
import { debounce, defaultDebounce } from '../../utils/util'
import { useUser } from '../../contexts/UserContext'
import { login as loginEmployee } from '../../services/employeeService'
import { useNavigate } from 'react-router-dom'

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
        // result = await loginCustomer(tmpUser)
      }
      
      if(result) {
        // navigate('../employees')
        const user = {
          username: username,
          token: result.token,
          expiresIn: result.expiresIn
        }
        login(user)
      } else {
        alert('帳號或密碼不存在')
      }
    })()
  }

  return (
    <form>
      <div className='mb-3'>
      <label>Username: </label>
      <input placeholder='Username' name='username' className='form-control'
          defaultValue={username} onChange={debouncedChangeHandler}/>
      </div>
      <div className='mb-3'>
      <label>Password: </label>
      <input placeholder='Password' name='password' className='form-control' type={isHidden ? 'password':'text'}
          defaultValue={password} onChange={(e) => debouncedChangeHandler(e)}/>
      <span onClick={handleShowPasswordClick}>  {isHidden ? 'Show':'Hide'} Password </span>
      </div>
      <Button onClick={handleLoginClick}>Login</Button>
    </form>
  )
}
