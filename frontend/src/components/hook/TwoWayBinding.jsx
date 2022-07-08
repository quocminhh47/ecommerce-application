import React, { useEffect, useState } from "react";


//2 ways binding


//this is get value from input field and show 

function App4() {

    const [name, setName] = useState('')

    const [mail, setMail] = useState('')

    const handleSubmit = () => {
        console.log({ name, mail });

    }
    return (
        <div style={{ padding: 32 }}>
            <input placeholder="Name"
                value={name}
                onChange={e => setName(e.target.value)}
            />
            <br />
            <input placeholder="Email"
                value={mail}
                onChange={e => setMail(e.target.value)}
            />
            <br />
            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}


//Respone from API:
const courses = [
    {
        name: 'A',
        price: '1'
    },
    {
        name: 'B',
        price: '2'
    },
    {
        name: 'C',
        price: '3'
    }
]
//radio button
function App5() {

    const [checked, setChecked] = useState();

    const handleClick = () => {
        //call api
        console.log({ id: checked });

    }
    return (
        <>
            <div style={{ padding: 32 }}>
                {
                    courses.map(course => (
                        <div key={course.price}>
                            <input
                                type='radio'
                                checked={course.price === checked}
                                onChange={() => setChecked(course.price)}
                            /> {course.name}
                        </div>

                    )
                    )
                }

            </div>
            <button onClick={handleClick}>Submit</button>
        </>
    )
}

// check box -> can apply to cart
function App6() {

    const [item, setItem] = useState([]);

    console.log(item);

    const handleClick = (id) => {

        setItem(prev => {
            const isChecked = item.includes(id);
            if (isChecked) {
                //unchecked
                return item.filter(dup => dup !== id);
            } else {
                return [...prev, id];
            }
        });
    }

    return (
        <>
            <div style={{ padding: 32 }}>
                {
                    courses.map(course => (
                        <div key={course.price}>
                            <input
                                type='checkbox'
                                checked={item.includes(course.price)}
                                onChange={() => handleClick(course.price)}
                            /> {course.name}
                        </div>

                    )
                    )
                }

            </div>
            <button >Submit</button>
        </>

    )
}

//todo list

function App7() {
    const [work, setWork] = useState('');
    const [works, setWorks] = useState([]);

    const inputHandle = (value) => {
        console.log(value);
        setWork(value);
    }

    const clickHandle = () => {
        setWorks(prev => [...prev, work]);
        setWork('');

    }

    return (
        <>
            <input
                value={work}
                onChange={(e) => inputHandle(e.target.value)} />
            <button onClick={clickHandle}>Submit</button>

            <ul>
                {
                    works.map((job, index) =>
                        <li key={index}>{job}</li>
                    )
                }
            </ul>
        </>
    )
}


//use effect
//1. useEffect(callback)
//goi moi khi component re-render
//goi callback sau khi them element vao dom
//2. useEffect(callback ,[])
//- chi goi callback 1 lan sua khi component mount
//3. useEffect(callback, [dependency])
//----------
//1. call back luon duoc goi sau khi component mounted

const tabs = ['posts', 'albums', 'todos']
function App8() {

    const [posts, setPosts] = useState([]);
    const [type, setType] = useState('posts');

    useEffect(() => {
        const url = `https://jsonplaceholder.typicode.com/${type}`;
        console.log(url)
        fetch(url)
            .then(res => res.json(url))
            .then(posts => setPosts(posts))

    }, [type]);

    const clickHandle = (tab) => {
        setType(tab);
    }

    return (
        <div>
            {
                tabs.map(tab =>
                    <button key={tab}
                        style={type === tab ? {
                            color: '#fff',
                            background: '#333'
                        } : {}
                        }
                        onClick={() => clickHandle(tab)}
                    >
                        {tab}
                    </button>
                )
            }
            <br />


            <ul>
                {
                    posts.map((post) =>
                        <li key={post.id}>{post.title}</li>
                    )
                }
            </ul>
        </div>
    )
}

export default App8;