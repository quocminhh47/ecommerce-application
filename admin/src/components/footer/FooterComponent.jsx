import React from 'react'
import  './css/Footer.css'

export default function FooterComponent() {
  return (
    <footer className="footer-16371">
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-9 text-center">
          <div className="footer-site-logo mb-4">
            <a href="#"> Quoc Minh</a>
          </div>
          <ul className="list-unstyled nav-links mb-5">
            <li><a href="#">About</a></li>
            <li><a href="#">Services</a></li>
            <li><a href="#">Press</a></li>
            <li><a href="#">Careers</a></li>
            <li><a href="#">FAQ</a></li>
            <li><a href="#">Legal</a></li>
            <li><a href="#">Contact</a></li>
          </ul>
          <div className="copyright">
            <p className="mb-0"><small>&copy; Quoc Minh. All Rights Reserved.</small></p>
          </div>


        </div>
      </div>
    </div>
  </footer>
  )
}
