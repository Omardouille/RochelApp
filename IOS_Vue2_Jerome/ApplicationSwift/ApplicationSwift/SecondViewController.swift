

import UIKit
import MapKit

class SecondViewController: UIViewController, MKMapViewDelegate
{
    @IBOutlet weak var mode: UIButton!
    
    
    @IBOutlet weak var lire: UIButton!

    @IBOutlet weak var ecrire: UIButton!
    
    @IBOutlet weak var carte: MKMapView!
    
    
    
    @IBOutlet var gestionTracer: UIPanGestureRecognizer!
    
    var listeAnnotations:[MKPointAnnotation] = [];
    var pointsParcours:[CLLocationCoordinate2D] = [];
    
    /*
    var dictionnaire : [String:[String:Double]] =
        [
            "parcours":
            [
                {"lat":30.30, "long":40.40},
                {"lat":20.20, "long":10.10}
            ]
        ]
    */
    
    var modeTrace:Bool = false;
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    
        carte.delegate = self
        
        let latitude:CLLocationDegrees = 46.151194;
        let longitude:CLLocationDegrees = -1.161005;
        
        //Distance entre l'utilisateur et le Nord/Sud
        let latitudeDelta:CLLocationDegrees = 0.03;
        let longitudeDelta:CLLocationDegrees = 0.03;
        
        //Zoom sur la carte
        let span:MKCoordinateSpan = MKCoordinateSpanMake(latitudeDelta, longitudeDelta);
        
        //Coordonnées géographiques
        let location:CLLocationCoordinate2D = CLLocationCoordinate2DMake(latitude, longitude);
        
        let region:MKCoordinateRegion = MKCoordinateRegionMake(location, span);
        
        //Aplication de la region à la carte
        carte.setRegion(region, animated: true);
        
        //Empêcher l'utilisateur de modifier la carte
        //carte.isUserInteractionEnabled = false;
        
        carte.isZoomEnabled = false;
        carte.isScrollEnabled = false;
        
        
        listeAnnotations.append(creerAnnotation(pLatitude: 46.149871, pLongitude: -1.168622, pTitre: "Point3"));
        
        
        placerAnnotation()
    }
    
    
    
    
    func creerAnnotation(pLatitude:Double, pLongitude:Double, pTitre:String) -> MKPointAnnotation
    {
        let lat:CLLocationDegrees = pLatitude;
        let long:CLLocationDegrees = pLongitude;
        let loc:CLLocationCoordinate2D = CLLocationCoordinate2DMake(lat, long);
        
        let annot = MKPointAnnotation();
        annot.coordinate = loc;
        annot.title = pTitre;
        
        return annot;
    }
    
    
    func placerAnnotation()
    {
        for annotation in listeAnnotations
        {
            print("point dessine");
            carte.addAnnotation(annotation);
        }
    }
    
    
    @IBAction func changerMode(sender: UIButton)
    {
        if ( modeTrace == false )
        {
            modeTrace = true;
            mode.setTitle("Mettre en mode observateur", for: UIControlState.normal)
        }
        else
        {
            modeTrace = false;
            mode.setTitle("Mettre en mode tracé", for: UIControlState.normal)
        }
    }
    
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?)
    {
        if (  modeTrace )
        {
            let premier = touches.first?.location(in: carte)
            let p = carte.convert(premier!, toCoordinateFrom: carte)
        
            listeAnnotations.append( creerAnnotation(pLatitude: p.latitude, pLongitude: p.longitude, pTitre: "Point de départ") );
        
            placerAnnotation()
        }
    }
    
    
    func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer
    {
        let polylineRenderer = MKPolylineRenderer(overlay: overlay)
        polylineRenderer.strokeColor = UIColor.red
        polylineRenderer.lineWidth = 2
        return polylineRenderer
    }
    
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?)
    {
        if ( modeTrace )
        {
            print("Tracé en cours");
            let premier = touches.first?.location(in: carte)
            //var deuxieme = touches.first?.location(in: carte)
            let p = carte.convert(premier!, toCoordinateFrom: carte)
            //var d = carte.convert(deuxieme!, toCoordinateFrom: carte)
        
            carte.add(MKPolyline(coordinates: [p, p], count: 2))
        
            pointsParcours.append(p);
            print(pointsParcours);
        }
    }
    
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?)
    {
        if ( modeTrace )
        {
        /*
             let listeInversee:[UITouch] = touches.reversed()
             let dernier = listeInversee.first?.location(in: carte)
             let d = carte.convert(dernier!, toCoordinateFrom: carte)
         */
            let dernier = touches.first?.location(in: carte)
            let d = carte.convert(dernier!, toCoordinateFrom: carte)
        
            listeAnnotations.append( creerAnnotation(pLatitude: d.latitude, pLongitude: d.longitude, pTitre: "Point d'arrivé") );
            
            placerAnnotation()
        }
    }
    
    
    
/*
    @IBAction func lireJSON(sender: UIButton)
    {
        let chemin = Bundle.main.path(forResource: "donnees", ofType: "json")
        let url = URL(fileURLWithPath: chemin!)
        
        //print(url)
        
        let donnees = try! Data(contentsOf: url)
        let objet = try! JSONSerialization.jsonObject(with: donnees, options: .allowFragments)
        
        print( objet )
    
        let message: String;

        if let str = (objet as! NSDictionary).value(forKey: "parcours")
        {
            message = str as? String ?? ""
            print("---------------")
            print( message )
            
            
        }
    }
    
    @IBAction func ecrireJSON(sender: UIButton)
    {
        let chemin = Bundle.main.path(forResource: "donnees", ofType: "json")
        let url = URL(fileURLWithPath: chemin!)
        
        let newMessage = "newMessage"
        

        for p in pointsParcours
        {
            
        }
        
        
        
        dictionnaire.setValue(newMessage, forKey: "parcours")
        dictionnaire.write(to: url, atomically: true)
        //dictionnaire.updateValue(9876, forKey: "pointDepart")
        
        print( dictionnaire )
    }
    */
    
}




























