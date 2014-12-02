//
//  challengeoneViewController.swift
//  APIChallenge
//
//  Created by Karl  Frazier on 12/1/14.
//  Copyright (c) 2014 Karl  Frazier. All rights reserved.
//

import UIKit
import SwiftHTTP
import JSONJoy

class challengeoneViewController: UIViewController {

    @IBOutlet weak var reversestringfield: UITextField!
    @IBOutlet weak var inputstringfield: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        struct ToReverse : JSONJoy {
            var string: String!
            init() {
                
            }
            init(_ decoder: JSONDecoder) {
                string = decoder["result"].string!
            }
        }

        var request = HTTPTask()
        var test: String!
        let params: Dictionary<String,String> = ["token":token]
        request.requestSerializer = JSONRequestSerializer()
        request.responseSerializer = JSONResponseSerializer()
        request.POST("http://challenge.code2040.org/api/getstring", parameters: params, success: {(response: HTTPResponse) in
            if (response.responseObject != nil) {
                let resp = ToReverse(JSONDecoder(response.responseObject!))
                if let string = resp.string {
                    println("String is: \(string)")
                    
                    //Create empty character Array.
                    var strArray:[Character] = [Character]()
                    
                    //Loop through each character in the String
                    for character in string {
                        //Insert the character in the Array variable.
                        strArray.append(character)
                    }
                    
                    //Create a empty string
                    var reversedStr:String = ""
                    
                    //Read the array from backwards to get the characters
                    for var index = strArray.count - 1; index >= 0;--index {
                        //Concatenate character to String.
                        reversedStr += String(strArray[index])
                    }
                    println("Reversed String is: \(reversedStr)")
                    test = string
                }
            }
            },failure: {(error: NSError, response: HTTPResponse?) in
                println("\(error)")
        })
        
       inputstringfield.text = test as String?
        

        // Do any additional setup after loading the view.
    }
    
   override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
