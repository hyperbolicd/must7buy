import React from 'react'
import Button from '../components/atoms/Button/Button'
import { ReactComponent as Logo} from '../assets/mosqito_icon.svg'
import AnnieIcon from '../assets/AnnieIcon'
import CartIcon from '../assets/CartIcon'

export default function ButtonTest() {
  return (
    <div>
        <h2>Button Test</h2>
        <div onClick={() => console.log('from div')} >
          <Button onClick={() => { console.log('hi') } } shape='round' size='s'>
            <AnnieIcon style={{width: '100%', height: '100%'}} />
          </Button>
          <div style={{ width: '100px', display: 'flex'}}>
            <Button onClick={() => { console.log('hi') } } shape='rectangle' variant='transparent' size='s'>
                <AnnieIcon style={{width: '100%', height: '100%'}} />
            </Button>
          </div>
          <Button onClick={() => { console.log('hi') } } shape='round' size='s'>
            Click
          </Button>
          <Button onClick={() => { console.log('hi') } } size='s'>

          </Button>
          <Button onClick={() => { console.log('hi') } } size='s' variant='secondary'>
            Click
          </Button>
          <Button onClick={() => { console.log('hi') } } variant='warning' size='s'>
            Click hey loooooooog
          </Button>
        </div>
        <div onClick={() => console.log('from div')}>
          <Button onClick={() => { console.log('hi') } } shape='round'>
             <CartIcon />
          </Button>
          <div style={{ width: '100px', display: 'flex'}}>
          <Button onClick={() => { console.log('hi') } } shape='rectangle' variant='transparent' >
             <CartIcon color='black'/>
          </Button>
          </div>
          <Button onClick={() => { console.log('hi') } } shape='round'>
            Click
          </Button>
          <Button onClick={() => { console.log('hi') } } >

          </Button>
          <Button onClick={() => { console.log('hi') } } variant='secondary'>
            Click
          </Button>
          <Button onClick={() => { console.log('hi') } } variant='warning'>
            Click hey loooooooog
          </Button>
        </div>
        <div onClick={() => console.log('from div')}>
          <Button onClick={() => { console.log('hi') } } shape='round' size='l'>
            <Logo style={{width: '100%', height: '100%'}} />
          </Button>
          <div style={{ width: '100px', display: 'flex'}}>
          <Button onClick={() => { console.log('hi') } } shape='rectangle' variant='transparent' size='l'>
            <Logo style={{width: '100%', height: '100%'}} />
          </Button>
          </div>
          <Button onClick={() => { console.log('hi') } } shape='round' size='l'>
            Click
          </Button>
          <Button onClick={() => { console.log('hi') } } size='l'>

          </Button>
          <Button onClick={() => { console.log('hi') } } size='l' variant='secondary'>
            Click
          </Button>
          <Button onClick={() => { console.log('hi') } } variant='warning' size='l'>
            Click hey loooooooog
          </Button>
        </div>
    </div>
  )
}
