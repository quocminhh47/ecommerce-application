import { useState } from "react";

//hook


const arrays = [100,200,300,400]
function App2() {
    //if like this: mỗi lần done use state nó sẽ render lại, và chạy lại total    
    //const total = arrays.reduce((total, cur) => total + cur);
    //console.log(total);
    //if this: nó lấy giá trị return làm initial state cho lần render sau
    // const [counter, setCounter] = useState(() => {
    //     const total = arrays.reduce((total, cur) => total+cur);
    //     console.log(total);
    //     return total;
    // });

    // const handleClick= () => {
    //     setCounter(dem => dem + 1); 
    // }

    // return (
    //     <div className="wrapper">
    //         <h1>{counter}</h1>
    //         <button onClick={handleClick} >Click</button>
    //     </div>
    // )

    const [info, setInfo] = useState({
        name: 'Quoc',
        age: '22',
        address: 'HCM'
    });

    const handleClick = () => {
         //neu muon add vao thay vi replace -> spread es6
           // ...info,
        setInfo({
            ...info,
            bio: 'Love'
        })
    }

    return (
        <div className="wrapper">
            <h1>{JSON.stringify(info)}</h1>
            <button onClick={handleClick} >Click</button>
        </div>
    )
}

export default App2;