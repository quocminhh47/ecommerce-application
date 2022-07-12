
function Test() {
    return (
        <>
            <h2 className="text-center">Sample Form</h2>
            <form className="form-horizontal">
                <div className="col-sm-6">
                    <div className="form-group">
                        <label className="control-label col-sm-4">Partner Name</label>
                        <div className="col-sm-8">
                            <input type="text" className="form-control"/>
                        </div>
                    </div>


                    <div className="form-group">
                        <label className="control-label col-sm-4">Partner Legal Name</label>
                        <div className="col-sm-8">
                            <input type="text" className="form-control"/>
                        </div>
                    </div>
                </div>

                <div className="col-sm-6">
                    <div className="form-group">
                        <label className="control-label col-sm-4">Partner Email ID</label>
                        <div className="col-sm-8">
                            <input type="text" className="form-control"/>
                        </div>
                    </div>

                    <div className="form-group">
                        <label className="control-label col-sm-4">Partner Mobile</label>
                        <div className="col-sm-8">
                            <input type="text" className="form-control"/>
                        </div>
                    </div>
                </div>

                <div className="col-sm-12">
                    <div className="form-group">
                        <label className="control-label col-sm-2">Partner Address</label>
                        <div className="col-sm-10">
                            <textarea type="text" className="form-control" rows="2"></textarea>
                        </div>
                    </div>
                </div>


                <div className="col-sm-6">
                    <div className="form-group">
                        <label className="col-sm-4 control-label">Contract Start Date</label>
                        <div className="col-sm-8">
                            <input type="text" className="date-start ml-5 form-control datepicker" placeholder="Date Start"/>
                        </div>
                    </div>
                </div>

                <div className="col-sm-6">
                    <div className="form-group">
                        <label className="col-sm-4 control-label">Contract Expiry Date</label>
                        <div className="col-sm-8">
                            <input type="text" className="date-end ml-5 form-control datepicker col-sm-8" placeholder="Date End"/>
                        </div>
                    </div>
                </div>

                <div className="col-sm-6">
                    <div className="form-group">
                        <label className="col-sm-4 control-label">Minimum Loan Amount</label>
                        <div className="col-sm-8">
                            <input type="text" className="form-control"/>
                        </div>
                    </div>
                </div>

                <div className="col-sm-6">
                    <div className="form-group">
                        <label className="col-sm-4 control-label">Maximum Loan Amount</label>
                        <div className="col-sm-8">
                            <input type="text" className="form-control"/>
                        </div>
                    </div>
                </div>

                <div className="col-sm-6">
                    <div className="form-group">
                        <label className="col-sm-4 control-label">Interest Rate</label>
                        <div className="col-sm-8">
                            <input type="text" className="form-control"/>
                        </div>
                    </div>
                </div>

                <div className="col-sm-6">
                    <div className="form-group">
                        <label className="col-sm-4 control-label">Deposit Amount</label>
                        <div className="col-sm-8">
                            <input type="text" className="form-control"/>
                        </div>
                    </div>
                </div>

                <div className="text-center">
                    <button className="btn btn-primary waves-effect waves-light " id="btn-submit">Save</button>
                </div>
                <input type="hidden" name="action" id="action" value="event_dialog_add_newpartnerdata" />
            </form>
        </>
    )
}

export default Test;