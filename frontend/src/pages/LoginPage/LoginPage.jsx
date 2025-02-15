import React from 'react'
import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import SubContent from '../../components/atoms/SubMenu/SubContent'
import LoginForm from '../../components/LoginForm/LoginForm'
import RegistryForm from '../../components/RegistryForm/RegistryForm'

export default function LoginPage() {
  return (
    <div>
        <Title>登入</Title>
        <Content>
            <SubContent position='center' gap='s'>
                <LoginForm />
                <RegistryForm />
            </SubContent>
        </Content>
    </div>
  )
}
