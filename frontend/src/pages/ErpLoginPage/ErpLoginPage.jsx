import Title from '../../components/atoms/Title/Title'
import Content from '../../components/atoms/Content/Content'
import LoginForm from '../../components/LoginForm/LoginForm'
import SubContent from '../../components/atoms/SubMenu/SubContent'

export default function ErpLoginPage() {

  return (
    <div>
      <Title>後台登入</Title>
        <Content>
          <SubContent position='center'>
            <LoginForm />
          </SubContent>
        </Content>
    </div>
  )
}