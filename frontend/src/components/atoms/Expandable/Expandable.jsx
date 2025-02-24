import React, { useState } from 'react';

const ExpandableComponent = ({ title, children }) => {
  // 狀態來控制是否展開
  const [isOpen, setIsOpen] = useState(false);

  // 切換展開/收起的函式
  const toggle = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div>
      <div 
        onClick={toggle} 
        style={{
          cursor: 'pointer',
          fontWeight: 'bold',
          padding: '10px',
          backgroundColor: '#f0f0f0',
          border: '1px solid #ccc',
          marginBottom: '5px',
        }}
      >
        {title} {isOpen ? '▲' : '▼'} {/* 展開/收起的箭頭 */}
      </div>
      {isOpen && (
        <div 
          style={{
            overflow: 'hidden', 
            maxHeight: isOpen ? '1000px' : '0', // 展開時顯示，收起時隱藏
            transition: 'max-height 0.3s ease-out', // 平滑過渡效果
            padding: isOpen ? '10px' : '0', // 根據是否展開調整 padding
            border: '1px solid #ccc',
            borderTop: 'none',
            backgroundColor: '#fafafa',
          }}
        >
          {children}
        </div>
      )}
    </div>
  );
};

export default ExpandableComponent;
