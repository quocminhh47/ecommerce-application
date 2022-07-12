import React from "react";
import { useParams } from 'react-router-dom'
//import axios from "axios";
import { useState } from "react";
import AuthService from '../../services/AuthService';
import { useEffect } from "react";
import FileUploadService from "../../services/FileUploadService";
import ProductService from "../../services/ProductService";
import BrandService from '../../services/BrandService';


function DetailProductComponent() {
    //id product from url
    const { productId } = useParams();
    //product info
    const [product, setProduct] = useState({});
    //list brand name
    const [brands, setBrands] = useState([]);
    //list images
    const [images, setImages] = useState([]);
    //product properties:
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState('');
    const [status, setStatus] = useState('');
    const [discount, setDiscount] = useState('');
    const [hidden, setHidden] = useState('');
    const [guarantee, setGuarantee] = useState('');
    const [gender, setGender] = useState('');
    const [isWaterProof, setIsWaterProof] = useState('');
    const [size, setSize] = useState('');
    const [brandId, setBrandId] = useState('');
    const [description, setDescription] = useState('');

    const [thumbnail, setThumbnail] = useState('');
    const [firstImage, setFirstImage] = useState({});
    const [secondImage, setSecondImage] = useState({});
    const [lastImage, setLastImage] = useState({});

    const PRODUCT_API_URL = "http://localhost:8080/user/api/products/" + productId;
    const BRAND_API_URL = "http://localhost:8080/user/api/brands";
    const IMAGE_UPLOAD_URL = "http://localhost:8080/admin/api/products/gallery";
    const PRODUCT_UPDATE_API_URL = "http://localhost:8080/admin/api/products/" + productId;



    //fetch product
    useEffect(() => {
        ProductService.getProductById(productId)
            .then(res => {
                setProduct(res.data);
                console.log(res.data)
                setImages(res.data.productImages)
                setThumbnail(res.data.thumbnail)
                if (res.data.productImages[0]) setFirstImage(res.data.productImages[0])
                if (res.data.productImages[1]) setSecondImage(res.data.productImages[1])
                if (res.data.productImages[2]) setLastImage(res.data.productImages[2])
            })
            .catch(e => {
                console.log(e);
                alert(e.response.data.message)
            })
    }, [])

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

    //upload image 
    const onThumbnailChange = (e) => {
        FileUploadService.fileUploadSetup(e)
        .then(res => {
            setThumbnail(res.data.url);
            alert(res.data.message);
        } )
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

    //brand onChange handler:
    const brandOnChange = () => {
        var select = document.getElementById("brandSelect");
        var value = select.options[select.selectedIndex].value;
        setBrandId(value)
    }

    const genderOnChange = () => {
        var select = document.getElementById("genderSelect");
        var value = select.options[select.selectedIndex].value;
        setGender(value)
    }

    const hiddenOnChange = () => {
        var select = document.getElementById("hiddenSelect");
        var value = select.options[select.selectedIndex].value;
        setHidden(value)
    }

    const waterProofOnChange = () => {
        var select = document.getElementById("waterProofSelect");
        var value = select.options[select.selectedIndex].value;
        setIsWaterProof(value)
    }

    //UPDATE PRODUCT:

    const updateProduct = () => {
        // const productPayload = {
        //     name: name,
        //     price: price,
        //     quantity: quantity,
        //     status: "status",
        //     thumbnail: thumbnail,
        //     discount: discount,
        //     hidden: "true",
        //     guaranteeTime: guarantee,
        //     gender: "true",
        //     isWaterProof: "true",
        //     size: size,
        //     brandId: brandId,
        //     description: description,
        //     images: [
        //         {
        //             id: firstImage.id,
        //             image: firstImage.image
        //         },
        //         {
        //             id: secondImage.id,
        //             images: secondImage.image
        //         },
        //         {
        //             id: lastImage.id,
        //             images: lastImage.image
        //         }
        //     ]
        // }

        // console.log(productPayload)

        // const token = localStorage.getItem("accessToken");
        // AuthService.checkUserAuth(token);
        // const header = {
        //     'Authorization': `Bearer ${token}`
        // }

        // axios.put(PRODUCT_UPDATE_API_URL, productPayload, header)
        //     .then(res => {
        //         console.log(res);
        //     })

    }


    return (
        <div className="container">

            <div className="form-row">

                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text">ID</span>
                        </div>
                        <input type="text" className="form-control" id="id" defaultValue={product.id} readOnly />
                    </div>
                </div>

                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text">Name</span>
                        </div>
                        <input type="text" className="form-control" id="name" defaultValue={product.name}
                            onChange={(e) => {
                                setName(e.target.value);
                                console.log(name);
                            }}
                        />
                    </div>
                </div>

                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" > Price</span>
                        </div>
                        <input type="text" className="form-control" id="price" defaultValue={product.price}
                            onChange={(e) => setPrice(e.target.value)}
                        />
                    </div>
                </div>
            </div>
            <div className="form-row">
                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text">Quantity</span>
                        </div>
                        <input type="number" className="form-control" id="quantity" defaultValue={product.quantity}
                            onChange={(e) => setQuantity(e.target.value)} />
                    </div>
                </div>

                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text">Status</span>
                        </div>
                        <input type="text" className="form-control" id="status" defaultValue={product.status}
                            onChange={(e) => setStatus(e.target.value)}
                        />
                    </div>
                </div>

                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text">Discount</span>
                        </div>
                        <input type="number" className="form-control" id="discount" defaultValue={product.discount}
                            onChange={(e) => setDiscount(e.target.value)} />
                    </div>
                </div>
            </div>
            <div className="form-row">
                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text">Guarantee (Moths)</span>
                        </div>
                        <input type="number" className="form-control" id="guarantee"
                            defaultValue={product.guaranteeTime} onChange={(e) => setGuarantee(e.target.value)} />
                    </div>
                </div>

                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text">Size (mm)</span>
                        </div>
                        <input type="text" className="form-control" id="size"
                            defaultValue={product.size} onChange={(e) => setSize(e.target.value)} />
                    </div>
                </div>

                <div className="col-md-4 mb-3">
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text">Brand</span>
                        </div>
                        <select className="form-select" id="brandSelect" onChange={
                            () => { brandOnChange() }}>
                            {
                                brands.map(brand =>
                                    <option value={brand.id} key={brand.id} name="brand"
                                        selected={brand.name == product.brandName ? true : false}
                                    >
                                        {brand.name}
                                    </option>
                                )
                            }
                        </select>
                    </div>
                </div>
            </div>
            <div className="form-row">

                <div className="input-group-prepend">
                    <span className="input-group-text">Gender</span>
                </div>
                <select className="form-select" id="genderSelect" style={{ marginRight: "230px" }} >

                    <option value={true} selected={product.gender ? true : false}
                        onChange={() => { genderOnChange() }} > Male</option>

                    <option value={false} selected={!product.gender ? true : false}
                        onChange={() => { genderOnChange() }}  > Female</option>
                </select>


                <div className="input-group-prepend">
                    <span className="input-group-text">Hidden</span>
                </div>
                <select className="form-select" id="hiddenSelect" style={{ marginRight: "240px" }}>

                    <option value={true} selected={product.hidden ? true : false}
                        onChange={() => { hiddenOnChange() }} > True</option>

                    <option value={false} selected={!product.hidden ? true : false}
                        onChange={() => { hiddenOnChange() }}  > False</option>
                </select>

                <div className="input-group-prepend">
                    <span className="input-group-text">Water proof </span>
                </div>
                <select className="form-select" id="waterProofSelect">

                    <option value={true} selected={product.isWaterProof ? true : false}
                        onChange={() => { waterProofOnChange() }} > True</option>

                    <option value={false} selected={!product.isWaterProof ? true : false}
                        onChange={() => { waterProofOnChange() }}  > False</option>
                </select>

            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea type="text" class="form-control" id="description"
                    defaultValue={product.description} onChange={(e) => setDescription(e.target.value)}>
                </textarea>
            </div>
            <div className="form-row">
                <div className="col-md-6 mb-3">
                    <label htmlFor="validationDefault03">Thumbnail&nbsp;</label>
                    <input type="file" id="validationDefault03" onChange={(e) => onThumbnailChange(e)} />
                    <a href={thumbnail}> {thumbnail} </a>
                </div>
            </div><hr />
            <br />
            <div className="form-row">
                <div className="col-md-6 mb-3">
                    <label htmlFor="validationDefault03">Image 1 &nbsp;</label>
                    <input type="file" id="validationDefault03" onChange={(e) => onFirstFileChange(e)} />
                    <a href={firstImage.image}> {firstImage.image} </a>

                </div>
            </div>
            <div className="form-row">
                <div className="col-md-6 mb-3">
                    <label htmlFor="validationDefault03">Image 2 &nbsp; </label>
                    <input type="file" id="validationDefault03" onChange={(e) => onSecondFileChange(e)} />
                    <a href={secondImage.image}> {secondImage.image} </a>
                </div>
            </div>
            <div className="form-row">
                <div className="col-md-6 mb-3">
                    <label htmlFor="validationDefault03">Image 3 &nbsp;</label>
                    <input type="file" id="validationDefault03" onChange={(e) => onThirdFileChange(e)} />
                    <a href={lastImage.image}> {lastImage.image} </a>


                </div>
            </div>
            <button className="btn btn-primary" type="button"
                onClick={() => updateProduct()}
            >Submit form
            </button>
        </div>
    )
}

export default DetailProductComponent;