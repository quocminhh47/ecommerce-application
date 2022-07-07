import React, { useState } from "react";

//van dung
const gifts =
    [
        'CPU I9',
        'RAM 32GB',
        'SSD 512GB'
    ]

function App3() {

    //const [gift, setGift] = useState('Chưa có phần thưởng');
    const [gift, setGift] = useState();//initial value: undefined -> gift:fasle

   const clickHandle = () => {
    let index = Math.floor((Math.random()) * gifts.length);
    setGift('Ban nhan duoc ' + gifts[index]);
   }

   

    return (
        <div style={{ padding: 32 }}>
            <h1>{gift || 'Chưa có phần thưởng'}</h1>
            <button onClick={clickHandle}>Lấy thưởng</button>
        </div>
    )
}


export default App3;