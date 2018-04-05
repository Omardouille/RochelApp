//
//  FirstViewController.m
//  Prj_Smart_Boat_SAMINE
//
//  Created by Mohammed Samine on 23/03/2018.
//  Copyright © 2018 Mohammed Samine. All rights reserved.
//

#import "FirstViewController.h"
#define METERS_PER_MILE 1609.344

@interface FirstViewController ()
@end


@implementation FirstViewController

@synthesize mapView,responseData,coordinate,points,lineView,polyline;



- (void)viewDidLoad {
    
    // Creation de la requette
    self.responseData = [NSMutableData data];
    NSURL *url = [NSURL URLWithString:@"http://127.0.0.1:55555"];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    NSURLConnection *connection = [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    
    
    
    self.points  = [[NSMutableArray alloc]init];
    [super viewDidLoad];
   
}

-(NSString*)getCoordonnees:(NSString*)trames
{
   
    NSArray * array = [[NSArray alloc] initWithArray:[trames componentsSeparatedByString:@"$"]];
    NSString * line  = array[3];
    NSArray * arrayCoors = [[NSArray alloc] initWithArray:[line componentsSeparatedByString:@","]];
    
    
    // --- Traitement de la latitude ---
    NSString * latitude = arrayCoors[2];
    NSString * latitudeDirection = arrayCoors[3];
    NSString * latFirst = [latitude substringToIndex:2];
    NSString * latEnd = [latitude substringFromIndex:2];
    float latitudeConvertie = [latFirst floatValue] + ([latEnd floatValue]/60);
    if([latitudeDirection isEqualToString:@"S"])
    {
        latitudeConvertie= -latitudeConvertie;
    }
    
    
    // --- Traitement de la longitude
    NSString * longitude = arrayCoors[4];
    NSString * longitudeDirection = arrayCoors[5];
    NSString * longFirst = [longitude substringToIndex:3];
    NSString * longEnd = [longitude substringFromIndex:3];
    float longitudeConvertie = [longFirst floatValue] + ([longEnd floatValue]/60);
    if([longitudeDirection isEqualToString:@"W"])
    {
        longitudeConvertie= -longitudeConvertie;
    }
    
    
    
    CLLocation *dataCoord = [[CLLocation alloc] initWithLatitude:latitudeConvertie longitude:longitudeConvertie];
    [self.points addObject:dataCoord];
    
    
    NSArray *myStrings = [[NSArray alloc] initWithObjects:@(latitudeConvertie).stringValue,@(longitudeConvertie).stringValue, nil];
    
    NSString *  donneestraites =@(latitudeConvertie).stringValue;
    donneestraites=[myStrings componentsJoinedByString:@" "];
    
    return donneestraites;
    
}

- (void)viewWillAppear:(BOOL)animated {
    // - Paramétrage du zoom initial : on souhaite avoir une vue de La Rochelle lorsqu'on lance l'application.
    // 1
    CLLocationCoordinate2D zoomLocation;
    zoomLocation.latitude = 46.145504;
    zoomLocation.longitude= -1.17861;
    
    // 2
    MKCoordinateRegion viewRegion = MKCoordinateRegionMakeWithDistance(zoomLocation, 0.8	*METERS_PER_MILE, 0.8*METERS_PER_MILE);
    
    // 3
    [mapView setRegion:viewRegion animated:YES];
    
}

#pragma mark - NSURLConnection Delegate Methods

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response{
    [self.responseData setLength:0];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    ///Ajuster le zoom de la carte
    MKCoordinateRegion mapRegion = mapView.region;
   
    NSString* donnee = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    NSString* coordonnes = [self getCoordonnees:donnee];
    
    
    
    NSArray* foo = [coordonnes componentsSeparatedByString: @" "];
    NSString* latitude = [foo objectAtIndex: 0];
    NSString* longitude = [foo objectAtIndex: 1];
    
    ///Affichage des coordonnes sur la carte
    mapRegion.center.latitude=[latitude floatValue];
    mapRegion.center.longitude=[longitude floatValue];
    
    
    
    coordinate = CLLocationCoordinate2DMake([latitude floatValue], [longitude floatValue]);
    MKPointAnnotation *point = [[MKPointAnnotation alloc] init];
    point.coordinate = coordinate;
    point.title = @"Point";
    
    
    
    
    CLLocationCoordinate2D coordinates[self.points.count];
   
    for(NSInteger index = 0;index<self.points.count;index++)
    {
        CLLocation *p =[self.points objectAtIndex:index];
        CLLocationCoordinate2D coordinate;
        
        coordinate.latitude = p.coordinate.latitude ;
        coordinate.longitude = p.coordinate.longitude;
        coordinates[index] = coordinate;
    }
    
    
    //Création de la ligne
    MKPolyline *polyline = [MKPolyline polylineWithCoordinates:coordinates count:self.points.count];
    //Ajouter la ligne à la carte ////
    [self.mapView addOverlay:polyline level:MKOverlayLevelAboveRoads];
    NSLog(@"%@",coordonnes);
    [self.mapView setDelegate:self];
    
    [self.mapView addAnnotation:point];
    self.mapView.frame = self.view.bounds;
    self.mapView.region = mapRegion;
    [self.responseData appendData:data];
    
}
#pragma mark - MKMapViewDelegate

//Tracer la Ligne en Rouge
- (MKOverlayRenderer *)mapView:(MKMapView *)mapView viewForOverlay:(id<MKOverlay>)overlay
{
    if ([overlay isKindOfClass:[MKPolyline class]])
    {
        MKPolylineRenderer *renderer = [[MKPolylineRenderer alloc] initWithPolyline:overlay];
        
        renderer.strokeColor = [[UIColor redColor] colorWithAlphaComponent:0.7];
        renderer.lineWidth   = 3;
        
        return renderer;
    }
    
    return nil;
}


- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    NSLog(@"Connection failed: %@", [error description]);
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    //Getting your response string
    NSString *responseString = [[NSString alloc] initWithData:self.responseData encoding:NSUTF8StringEncoding];
    self.responseData = nil;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}


@end
