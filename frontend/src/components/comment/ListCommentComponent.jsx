import React, { useEffect } from 'react'
import './css/style.css'
// import CommentService from '../../services/CommentService'

export default function ListCommentComponent(props, userAccess) {


    return (
        <div className="container bootdey">
            <div className="col-md-12 bootstrap snippets">
                <div className="panel">
                    <div className="panel-body">
                        <textarea className="form-control" rows="2" placeholder="What are you thinking?"></textarea>
                        <div className="mar-top clearfix">
                            <button className="btn btn-sm btn-primary pull-right" type="submit"><i className="fa fa-pencil fa-fw"></i> Share</button>
                            <a className="btn btn-trans btn-icon fa fa-video-camera add-tooltip" href="#"></a>
                            <a className="btn btn-trans btn-icon fa fa-camera add-tooltip" href="#"></a>
                            <a className="btn btn-trans btn-icon fa fa-file add-tooltip" href="#"></a>
                        </div>
                    </div>
                </div>
                <div className="panel">
                    <div className="panel-body">
                        {
                            props.commentContent.map(cmt =>
                                <div className="media-block">
                                    <a className="media-left" href="#"><img className="img-circle img-sm" alt="Profile Picture"
                                        src="https://res.cloudinary.com/duoih0eqa/image/upload/v1657952418/Capture_o1rjgp.jpg"
                                        style={{ marginRight: "15px" }} /></a>
                                    <div className="media-body">
                                        <div className="mar-btm">
                                            <a href="#" className="btn-link text-semibold media-heading box-inline">{cmt.userName}</a>
                                            <p className="text-muted text-sm"><i className="fa fa-mobile fa-lg"></i> {cmt.cmtTime}</p>
                                        </div>
                                        <p>{cmt.message}</p>
                                        <div className="pad-ver">
                                            <div className="btn-group">
                                                <a className="btn btn-sm btn-default btn-hover-success" href="#"><i className="fa fa-thumbs-up"></i></a>
                                                <a className="btn btn-sm btn-default btn-hover-danger" href="#"><i className="fa fa-thumbs-down"></i></a>
                                            </div>
                                            <a className="btn btn-sm btn-default btn-hover-primary" href="#">{cmt.userName == userAccess ? 'Delete' : ''}</a>
                                        </div>
                                        <hr />
                                    </div>
                                </div>
                            )
                        }


                    </div>
                </div>
            </div>
        </div>
    )
}
