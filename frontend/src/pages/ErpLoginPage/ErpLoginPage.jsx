import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import LoginForm from '../../components/LoginForm/LoginForm'

export default function ErpLoginPage() {

  return (
    <div>
      <Title>後台登入</Title>
        <Content>
          <LoginForm />
        </Content>
    </div>
  )
}