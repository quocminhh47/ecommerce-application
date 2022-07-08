import React from "react";

function BreadCrumbComponent(props) {
    return (
        <div className="breadcrumb-option">
        <div className="container">
            <div className="row">
                <div className="col-lg-12">
                    <div className="breadcrumb__links">
                        <a href="./index.html"><i className="fa fa-home"></i> Home</a>
                        <a href="#">Chi tiết sản phẩm </a>
                        <span>{props.name}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    )
}

export default BreadCrumbComponent;