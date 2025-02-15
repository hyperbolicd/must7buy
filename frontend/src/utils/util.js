import BLANK_USER from '../assets/blank-profile-picture-973460_1280.webp'

function getFileTypeFromMagicNumber(base64) {
    const magicNumbers = atob(base64).slice(0, 4); // 解碼並取前 4 個字元
  
    if (magicNumbers.startsWith("\x89PNG")) {
      return "image/png";
    } else if (magicNumbers.startsWith("\xFF\xD8\xFF")) {
      return "image/jpeg";
    } else if (magicNumbers.startsWith("GIF8")) {
      return "image/gif";
    }
    return "unknown";
  }

export function getUrlFromBase64(base64) {
    if(!base64) {
        return BLANK_USER
    } else {
        return `data:${getFileTypeFromMagicNumber(base64)};base64,${base64}`
    }
}

export function defaultDebounce(func) {
    return debounce(func, 200)
}

export function debounce(func, delay) {
    let timer;

    return function (...args) {
        clearTimeout(timer)
        timer = setTimeout(() => {
            func(...args)
        }, delay)
    }
}

export function getDataURLFromFile(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => resolve(reader.result)
        reader.onerror = reject
    })
}

export function getBase64FromUrl(url) {
    return url.split(';base64,')[1]
}

export const imageAcceptType = 'image/png, image/jpeg'

export const imageAcceptSize = 4 * 1024 * 1024// 4MB

export function validImageSize(size) {
    return size <= imageAcceptSize
}

export function getThumbnailUrl(imageUrl) { // 'product/xxx.jpg' -> 'product/thumbnail/thumbnail-xxx.jpg'
    return imageUrl.split('/')[0] + '/thumbnail/thumbnail-' + imageUrl.split('/')[1]
}

export function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}