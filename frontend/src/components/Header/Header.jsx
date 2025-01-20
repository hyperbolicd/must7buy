import React, { useState } from 'react'
// import logo from './logo.svg';
import styles from './Header.module.css'
import Logo from '../../assets/Logo/Logo'
import Button from '../atoms/Button/Button'
import { useNavigate } from 'react-router-dom'
import LoginIcon from '../../assets/LoginIcon'
import CartIcon from '../../assets/CartIcon'
import ArrowRightIcon from '../../assets/ArrowRightIcon'

export default function Header() {
  const navigate = useNavigate()
  const categories = [
    { name: '衣服', keywords: 'cloths'},
    { name: '帽子', keywords: 'hats'},
    { name: '褲子', keywords: 'pants'},
    { name: '大衣', keywords: 'coats'},
    { name: '洋裝', keywords: 'dress'},
    { name: '玩偶', keywords: 'dolls'},
    { name: '吊飾', keywords: 'keyholders'},
    { name: '立牌', keywords: 'acrylics'},
    { name: '相片', keywords: 'pictures'},
    { name: '聲音', keywords: 'voices'},
  ]
  const defaultOption = 'none'
  const [option, changeOption] = useState(defaultOption)
  const idols = [
    { name: 'Red', keywords: 'red'},
    { name: 'Yellow', keywords: 'yellow'},
    { name: 'Green', keywords: 'green'},
    { name: 'Black', keywords: 'black'},
    { name: 'Pink', keywords: 'pink'},
  ]

  function handleLogoClick() {
    navigate('/home')
    changeOption(defaultOption)
  }

  function handleLoginClick() {
    navigate('/login')
  }

  function handleCartClick() {
    navigate('/cart')
  }

  function handleAllItemsClick() {
    navigate('/search')
    changeOption(defaultOption)
  }

  function handleCategoryClick(category) {
    navigate(`search?category=${category}`)
  }

  return (
    <div>
      <header className={styles.header}>
        <div className={styles.logo}>
          <Button variant='transparent' onClick={handleLogoClick} >
            <Logo />
          </Button>
        </div>
        <div className={styles.menu}>
          <ul className={styles.ul}>
            <li className={styles.li}>
              <Button variant='transparent' onClick={handleAllItemsClick}>
                全商品
              </Button>
            </li>
            <li className={styles.li}>
              <Button variant='transparent' onClick={() => {
                const code = 'A'
                if(option === code) changeOption(defaultOption)
                else changeOption(code) }}>
                分類
              </Button>
            </li>
            <li className={styles.li}>
              <Button variant='transparent' onClick={() => {
                const code = 'B'
                if(option === code) changeOption(defaultOption)
                else changeOption(code) }}>
                偶像
              </Button>
            </li>
          </ul>
        </div>
        <div className={styles.search}>

        </div>
        <div className={styles.cart}>
          <Button variant='transparent' onClick={handleCartClick}>
            <CartIcon width='50%'/>
          </Button>
        </div>
        <div className={styles.profile}>
          <Button variant='transparent' onClick={handleLoginClick}>
            <LoginIcon width='50%'/>
          </Button>
        </div>
        { option === 'A' &&
          <div className={styles.submenu}>
            <div className={styles.ulTitle}>
              <span>分類一覽</span>
            </div>
            <div className={styles.ulDiv}>
              <ul className={styles.ulSecondary}>
                { 
                  categories.map(category => {
                    return(
                      <li className={styles.liSecondary} key={category.keywords}>
                        <Button variant='transparent' onClick={() => {handleCategoryClick(category.keywords)}}>
                          <ArrowRightIcon height='20px' width='20px' color='rgb(200, 200, 200)' /> {category.name}
                        </Button>
                      </li>
                    )
                  }) 
                }
              </ul>
            </div>
          </div>
        }
        { option === 'B' &&
          <div className={styles.submenu}>
            <div className={styles.ulTitle}>
              <span>偶像一覽</span>
            </div>
            <div className={styles.ulDiv}>
              <ul className={styles.ulSecondary}>
                { 
                  idols.map(idol => {
                    return(
                      <li className={styles.liSecondary} key={idol.keywords}>
                        <Button variant='transparent' onClick={() => {handleCategoryClick(idol.keywords)}}>
                          <ArrowRightIcon height='20px' width='20px' color='rgb(200, 200, 200)' /> {idol.name}
                        </Button>
                      </li>
                    )
                  }) 
                }
              </ul>
            </div>
          </div>
        }
      </header>
    </div>
  )
}
