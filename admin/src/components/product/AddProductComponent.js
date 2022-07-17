import React from 'react'
import BrandService from '../../services/BrandService'
import { useState, useEffect } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom';
import ProductService from '../../services/ProductService';
import FileUploadService from '../../services/FileUploadService';
import LoginService from '../../services/LoginService';
import HeaderComponent from '../header/HeaderComponent';



export default function AddProductComponent() {
    const PRODUCTS_API_BASE_URL = "http://localhost:8080/user/api/products/";
    const PRODUCTS_API_UPDATE_BASE_URL = "http://localhost:8080/admin/api/products/";

    const { productId } = useParams();
    const navigate = useNavigate();
    const [loginStatus, setLoginStatus] = useState();
    const [updatePage, setUpdatePage] = useState({})

    if (!LoginService.checkAuthorization()) {
        navigate('/login')
    }

    //fetch list brands
    const [brands, setBrands] = useState([]);

    // product props
    const [name, setName] = useState('');
    const [price, setPrice] = useState(0);
    const [quantity, setQuantity] = useState(0);
    const [status, setStatus] = useState('');
    const [discount, setDiscount] = useState(0);
    const [hidden, setHidden] = useState(false);
    const [guarantee, setGuarantee] = useState(0);
    const [gender, setGender] = useState(true);
    const [isWaterProof, setIsWaterProof] = useState(true);
    const [size, setSize] = useState(1);
    const [brandId, setBrandId] = useState();
    const [description, setDescription] = useState('');
    const [thumbnail, setThumbnail] = useState('');
    const [brandName, setBrandName] = useState('');

    const [firstImage, setFirstImage] = useState({});
    const [secondImage, setSecondImage] = useState({});
    const [lastImage, setLastImage] = useState({});

    //validation err response
    const [nameErr, setNameErr] = useState('');
    const [priceErr, setPriceErr] = useState('');
    const [quantityErr, setQuantityErr] = useState('');
    const [statusErr, setStatusErr] = useState('');
    const [discountErr, setDiscountErr] = useState('');
    const [guaranteeErr, setGuaranteeErr] = useState('');
    const [sizeErr, setSizeErr] = useState('');
    const [thumbnailErr, setThumbnailErr] = useState('')
    const [brandErr, setBrandErr] = useState();

    //fetch brands
    useEffect(() => {
        BrandService.fetchBrandName()
            .then(res => {
                setBrands(res.data.brandContent)
            })
            .catch(error => {
                console.log(error);
                alert(error.response.data.message)
            });
    }, [])
    // ====fetch product info ===========
    useEffect(() => {
        ProductService.getProductById(productId)
            .then(res => {
                const data = res.data;
                setName(data.name)
                setPrice(data.price)
                setQuantity(data.quantity)
                setStatus(data.status)
                setDiscount(data.discount)
                setGuarantee(data.guaranteeTime)
                setSize(data.size)
                setHidden(data.hidden)
                setIsWaterProof(data.isWaterProof)
                setThumbnail(data.thumbnail)
                setDescription(data.description)
                setGender(data.gender);
                setBrandName(data.brandName)
                setBrandId(data.brandId)

                setFirstImage(data.productImages[0])
                setSecondImage(data.productImages[1])
                setLastImage(data.productImages[2])

                setLoginStatus(true)

            })
            .catch(err => {
                console.log(err)
                if (err.response.status == '403') {
                    navigate('/login')
                }
            })
    }, []);

    //upload image 
    const onThumbnailChange = (e) => {
        FileUploadService.fileUploadSetup(e)
            .then(res => {
                setThumbnail(res.data.url);
                alert(res.data.message);
            })
            .catch(error => {
                alert(error.response.data.message);
                console.log(error);
            })
    }

    const onFirstFileChange = (e) => {
        FileUploadService.fileUploadSetup(e)
            .then(res => {
                let updateImage = {
                    id: firstImage.id,
                    image: res.data.url
                }
                setFirstImage(updateImage);
                alert(res.data.message);
                console.log(updateImage);
            })
            .catch(error => {
                alert(error.response.data.message);
                console.log(error);
            })
    }

    const onSecondFileChange = (e) => {
        FileUploadService.fileUploadSetup(e)
            .then(res => {
                let updateImage = {
                    id: secondImage.id,
                    image: res.data.url
                }
                setSecondImage(updateImage);
                alert(res.data.message);
                console.log(updateImage)
            })
            .catch(error => {
                alert(error.response.data.message);
                console.log(error);
            })
    }

    const onThirdFileChange = (e) => {
        FileUploadService.fileUploadSetup(e)
            .then(res => {
                let updateImage = {
                    id: lastImage.id,
                    image: res.data.url
                }
                setLastImage(updateImage);
                alert(res.data.message);
            })
            .catch(error => {
                alert(error.response.data.message);
                console.log(error);
            })
    }

    // ======================Create/ Update Product hanlder=====================
    const createProduct = (e) => {
        e.preventDefault();
        const images = [firstImage, secondImage, lastImage];
        const productPayload = {
            name: name,
            price: price,
            quantity: quantity,
            status: status,
            discount: discount,
            hidden: hidden,
            guaranteeTime: guarantee,
            gender: gender,
            isWaterProof: isWaterProof,
            size: size,
            brandId: brandId,
            description: description,
            thumbnail: thumbnail,
            images: images
        }
        console.log(productPayload)
        if (productId) {
            const updateUrl = PRODUCTS_API_UPDATE_BASE_URL + productId;
            ProductService.updateProduct(productPayload, updateUrl)
                .then(res => {
                    console.log(res)
                    alert("Update success!")
                    navigate('/')
                })
                .catch(e => {
                    alert("Update failed, check again")
                    console.log(e)
                    const validationMess = e.response.data.validationErrors;
                    if (validationMess) {
                        setNameErr(validationMess.name)
                        setPrice(validationMess.price)
                        setQuantityErr(validationMess.quantity)
                        setStatusErr(validationMess.status)
                        setDiscountErr(validationMess.discount)
                        setGuaranteeErr(validationMess.guaranteeTime)
                        setThumbnailErr(validationMess.thumbnail)
                        setSizeErr(validationMess.size)
                        setUpdatePage(e.response.data)
                        setBrandErr(validationMess.brandId)
                    }
                })
        }
        else {
            console.log(productPayload)
            ProductService.createNewProduct(productPayload)
                .then(res => {
                    alert("Create product success")
                    console.log('res' + res.data);
                    window.location.href = "/"
                })
                .catch(e => {
                    const validationMess = e.response.data.validationErrors;
                    if (validationMess) {
                        setNameErr(validationMess.name)
                        setPriceErr(validationMess.price)
                        setQuantityErr(validationMess.quantity)
                        setStatusErr(validationMess.status)
                        setDiscountErr(validationMess.discount)
                        setGuaranteeErr(validationMess.guaranteeTime)
                        setThumbnailErr(validationMess.thumbnail)
                        setSizeErr(validationMess.size)
                        setBrandErr(validationMess.brandId)
                        setUpdatePage(e.response.data)
                    }
                    if (e.response.status == 400) {
                        console.log(e)
                        alert("Data invalid, try again!")
                    }
                    else {
                        alert("Server error, try again")
                    }
                })
        }

    }

    return (
        <>
            <HeaderComponent status={loginStatus} />
            <div className="container">
                <div className="form-row">

                    <div className="col-md-4 mb-3">
                        <div>
                            <label htmlFor='name'>Name : <span style={{ color: "red" }}>{nameErr}</span></label>
                            <input type="text" className="form-control" id="name"
                                value={name} onChange={(e) => setName(e.target.value)} />
                        </div>
                    </div>

                    <div className="col-md-4 mb-3">
                        <div >
                            <label htmlFor='price'>Price : <span style={{ color: "red" }}>{priceErr}</span></label>
                            <input type="text" className="form-control" id="price"
                                value={price} onChange={(e) => setPrice(e.target.value)} />
                        </div>
                    </div>
                    <div className="col-md-4 mb-3">
                        <div >
                            <label htmlFor='quantity'>Quantity :<span style={{ color: "red" }}>{quantityErr}</span></label>
                            <input type="number" className="form-control" id="quantity"
                                value={quantity} onChange={(e) => setQuantity(e.target.value)} />
                        </div>
                    </div>
                </div>

                <div className="form-row">

                    <div className="col-md-4 mb-3">
                        <div >
                            <label htmlFor='status'>Status :<span style={{ color: "red" }}>{statusErr}</span></label>
                            <input type="text" className="form-control" id="status"
                                value={status} onChange={(e) => setStatus(e.target.value)} />
                        </div>
                    </div>

                    <div className="col-md-4 mb-3">
                        <div >
                            <label htmlFor='discount'>Discount :<span style={{ color: "red" }}>{discountErr}</span></label>
                            <input type="number" className="form-control" id="discount"
                                value={discount} onChange={(e) => setDiscount(e.target.value)} />
                        </div>
                    </div>

                    <div className="col-md-4 mb-3">
                        <div >
                            <label htmlFor='discount'>Guarantee (Months) :<span style={{ color: "red" }}>{guaranteeErr}</span></label>
                            <input type="number" className="form-control" id="guarantee"
                                value={guarantee} onChange={(e) => setGuarantee(e.target.value)} />
                        </div>
                    </div>
                </div>

                <div className="form-row">

                    <div className="col-md-4 mb-3">
                        <div >
                            <label htmlFor='discount'>Size (mm) (Months) :<span style={{ color: "red" }}>{sizeErr}</span></label>
                            <input type="text" className="form-control" id="size"
                                value={size} onChange={(e) => setSize(e.target.value)} />
                        </div>
                    </div>

                    <div className="col-md-4 mb-3">
                        <div >
                            <label htmlFor='brandSelect'>Brand: <span style={{ color: "red" }}>{brandErr}</span> </label>
                            <br />
                            <select className="form-select" id="brandSelect"                            
                                onChange={(e) => {
                                    console.log(e.target.value)
                                    setBrandId(e.target.value)
                                }} >
                                <option>---SELECT---</option>
                                {
                                    brands.map(brand =>
                                        <option value={brand.id} key={brand.id} name="brand"
                                            selected={brand.name == brandName ? true : false}
                                        >
                                            {brand.name}
                                        </option>
                                    )
                                }
                            </select>
                        </div>
                    </div>

                    <div className="col-md-4 mb-3">
                        <div >
                            <label htmlFor='genderSelect'>Gender:  </label><br />
                            <select className="form-select" id="genderSelect"
                                onChange={(e) => {
                                    setGender(e.target.value)
                                    console.log(e.target.value)
                                }}>
                                <option value={'true'} selected={gender} > Male</option>

                                <option value={'false'} selected={!gender} > Female</option>
                            </select>
                        </div>
                    </div>

                </div>

                <div className="form-row" style={{ display: "center" }}>

                    <div className="col-md-4 mb-3">
                        <label htmlFor='hiddenSelect'>Hidden from system </label><br />
                        <select className="form-select" id="hiddenSelect" style={{ marginRight: "10px" }}
                            onChange={(e) => {
                                setHidden(e.target.value)
                            }}>

                            <option value={'true'} selected={hidden}> True</option>

                            <option value={'false'} selected={!hidden} > False</option>
                        </select>
                    </div>


                    <div className="col-md-4 mb-3">
                        <label htmlFor='waterProofSelect'>Water Proof </label><br />
                        <select className="form-select" id="waterProofSelect" onChange={(e) => {
                            setIsWaterProof(e.target.value)
                        }}>
                            <option value={'true'} selected={isWaterProof}>
                                True
                            </option>

                            <option value={'false'} selected={!isWaterProof} > False</option>
                        </select>
                    </div>

                </div>
                <div className='form-row'>
                </div>

                <br />

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea type="text" class="form-control" id="description"
                        value={description}
                        onChange={(e) => {
                            setDescription(e.target.value);
                        }}>
                    </textarea>
                </div>
                <div className="form-row">
                    <div className="col-md-6 mb-3">
                        <label htmlFor="validationDefault03">Thumbnail : <span style={{ color: "red" }}>{thumbnailErr}</span></label>
                        <input type="file" id="validationDefault03" onChange={(e) => {
                            onThumbnailChange(e)
                        }} />
                        <a href={thumbnail} style={{ color: "blue" }}> {thumbnail} </a>
                    </div>
                </div><hr />
                <br />
                <div className="form-row">
                    <div className="col-md-6 mb-3">
                        <label htmlFor="validationDefault03">Image 1 &nbsp;</label>
                        <input type="file" id="validationDefault03" onChange={(e) => {
                            onFirstFileChange(e);
                        }} />
                        <a href={firstImage ? firstImage.image : 'No image yet'} style={{ color: "blue" }}
                        >
                            {firstImage ? firstImage.image : 'No image yet'}
                        </a>

                    </div>
                </div>
                <div className="form-row">
                    <div className="col-md-6 mb-3">
                        <label htmlFor="validationDefault03">Image 2 &nbsp; </label>
                        <input type="file" id="validationDefault03" onChange={(e) => {
                            onSecondFileChange(e);
                        }} />
                        <a href={secondImage ? secondImage.image : 'No image yet'} style={{ color: "blue" }}
                        >{secondImage ? secondImage.image : 'No image yet'} </a>
                    </div>
                </div>
                <div className="form-row">
                    <div className="col-md-6 mb-3">
                        <label htmlFor="validationDefault03">Image 3 &nbsp;</label>
                        <input type="file" id="validationDefault03" onChange={(e) => {
                            onThirdFileChange(e);
                        }} />
                        <a href={lastImage ? lastImage.image : 'No image yet'} style={{ color: "blue" }}
                        > {lastImage ? lastImage.image : 'No image yet'} </a>


                    </div>
                </div>
                <button className="btn btn-primary" type="button" onClick={(e) => createProduct(e)}
                >Submit form
                </button>
            </div>

        </>
    )
}
