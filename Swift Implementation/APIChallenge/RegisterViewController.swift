//
//  RegisterViewController.swift
//  APIChallenge
//
//  Created by Karl  Frazier on 12/1/14.
//  Copyright (c) 2014 Karl  Frazier. All rights reserved.
//

import UIKit
import SwiftHTTP
import JSONJoy

class RegisterViewController: UIViewController {

    @IBOutlet weak var tokenfield: UITextField!
    var ctoken: String?
    override func viewDidLoad() {
        super.viewDidLoad()
        struct Token : JSONJoy {
            var token: String?
            init() {
                
            }
            init(_ decoder: JSONDecoder) {
                token = decoder["token"].string
            }
        }
        var request = HTTPTask()
        let params: Dictionary<String,String> = ["email": "kmjf@princeton.edu", "github":"https://github.com/darthfrazier/API-Challenge"]
        request.requestSerializer = JSONRequestSerializer()
        request.responseSerializer = JSONResponseSerializer()
        request.POST("http://challenge.code2040.org/api/register", parameters: params, success: {(response: HTTPResponse) in
            if (response.responseObject != nil) {
                let resp = Token(JSONDecoder(response.responseObject!))
                if let token = resp.token {
                println("Token is: \(token)")
                ctoken = token
                self.tokenfield.text = token
                }
            }
            },failure: {(error: NSError, response: HTTPResponse?) in
                println("\(error)")
        })


        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func viewDidAppear() {
        
        
        
        
        /*var request = HTTPTask()
        //we have to add the explicit type, else the wrong type is inferred. See the vluxe.io article for more info.
        let params: Dictionary<String,AnyObject> = ["dict": ["email": "kmjf@princeton.edu", "github":"https://github.com/darthfrazier"]]
        request.POST("http://challenge.code2040.org/api/register", parameters: params, success: {(response: HTTPResponse) in
            let json = response.responseObject! as Dictionary<String,String>
            println(json["token"]!)
            self.tokenfield.text = json["token"]!
            
            },failure: {(error: NSError, response: HTTPResponse?) in
        
                
        })*/
        
    }
        

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    if (segue.identifier == "back") {
        var challenges = segue!.destinationViewController as DetailViewController;
        challenges.toPass = ctoken
    }

}
