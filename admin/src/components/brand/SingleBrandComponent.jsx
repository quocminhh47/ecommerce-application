import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import BrandService from '../../services/BrandService';
import './singleBrand.css'
import FileUploadService from '../../services/FileUploadService';
import HeaderComponent from '../header/HeaderComponent';
import LoginService from '../../services/LoginService';

function SingleBrandComponent() {

    let navigate = useNavigate();

    const { brandId } = useParams();

    const [brand, setBrand] = useState({})
    const [name, setName] = useState('')
    const [thumbnail, setThumbnail] = useState('')
    const [description, setDescription] = useState('')
    const [loginStatus, setLoginStatus] = useState()

    if (!LoginService.checkAuthorization()) {
        navigate('/login')
    }

  

    //fetch brand by id
    useEffect(() => {
        if (brandId) {
            BrandService.fetchBrandById(brandId)
                .then(res => {
                    console.log(res.data)
                    setBrand(res.data)
                    setName(res.data.name)
                    setThumbnail(res.data.thumbnail)
                    setDescription(res.data.description)
                    setLoginStatus(LoginService.checkLoginStatus(res.data));
                })
                .catch(err => {
                    console.log(err)
                    if (err.response.status == '403') {
                        navigate('/login')
                    }
                })
        }

    }, [])

    const onThumbnailChange = (e) => {
        FileUploadService.fileUploadSetup(e)
            .then(res => {
                console.log(res.data)
                setThumbnail(res.data.url)
                alert(res.data.message);
            })
            .catch(err => console.log(err))
    }

    const createUpdateBrand = () => {
        const brandPayload = { name, thumbnail, description };

        if (brandId) {
            BrandService.upDateBrand(brandPayload, brandId)
                .then(res => {
                    console.log(res)
                    alert("Update success")
                    navigate('/brands')
                })
                .catch(err => {
                    console.log(err)
                    alert("Failed")
                })
        } else {
            BrandService.createNewBrand(brandPayload)
                .then(res => {
                    if (res.status == 201) {
                        console.log(res.data)
                        alert("Create new brand succes!")
                        navigate('/brands')
                    }
                })
                .catch(err => {
                    console.log(err)
                    if (err.response) {
                        alert(err.response.data.validationErrors.name)
                    }
                    else {
                        alert("Create brand failed")
                    }
                })
        }
    }

    return (
        <>
            <HeaderComponent status={loginStatus} />
            <section class="contact spad">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-6 col-md-6">
                            <div class="contact__content">
                                <div class="contact__form">
                                    <h5>BRAND DETAIL</h5>
                                    <form action="#">
                                        <label hidden={brandId ? false : true} >ID</label>
                                        <input type="text" name="id" value={brandId}
                                            hidden={brandId ? false : true} />

                                        <label>Name</label>
                                        <input type="text" value={name}
                                            onChange={(e) => setName(e.target.value)}

                                        />

                                        <label>Thumbnail</label>
                                        <input type="file" onChange={(e) => onThumbnailChange(e)} />
                                        <a href={thumbnail} style={{ color: "blue" }}> {thumbnail} </a>
                                        <br />
                                        <label>Description</label>
                                        <textarea value={description}
                                            onChange={(e) => {
                                                setDescription(e.target.value)
                                            }}
                                        ></textarea>
                                        <button type="button" class="site-btn"
                                            onClick={() => createUpdateBrand()}>Save</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6">
                            <div class="contact__map">
                                <img
                                    src={thumbnail}
                                    height="680" style={{ border: 0 }} />

                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )


}

export default SingleBrandComponent;