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
@synthesize responseData;


- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.responseData = [NSMutableData data];
    // Creation de la requette
    NSURLRequest *requette = [NSURLRequest requestWithURL:[NSURL URLWithString:@"http://127.0.0.1:55555"]];
    
    // Creation du url de connection
    NSURLConnection *connection = [[NSURLConnection alloc] initWithRequest:requette delegate:self];
    
    [super viewDidLoad];
    
    int coordinatesIndex = 0;
    
}

-(NSString*)getCoordonnees:(NSString*)trame {
    NSArray * array = [[NSArray alloc] initWithArray:[trame componentsSeparatedByString:@"$"]];
    NSString * line  = array[3];
    
    NSArray * arrayCoors = [[NSArray alloc] initWithArray:[line componentsSeparatedByString:@","]];
    
    NSString * coordonees = arrayCoors[1]; // Récupération de la latitude
    coordonees = [coordonees stringByAppendingString:@";"];
    coordonees = [coordonees stringByAppendingString:arrayCoors[3]]; // Récupération de la longitude
    
    NSLog(@"%@", coordonees);
    
    
    
    // --- Traitement de la latitude ---
    NSString * latitude = arrayCoors[1];
    
    
    char * tmp = [latitude UTF8String];
    
    NSString *minLat = @"";
    NSString *lat = @"";
    for(int i = latitude.length; i >= 0; i--){
        if(i > latitude.length-7)
            minLat = [NSString stringWithFormat:@"%c%@", tmp[i], minLat];
        else
            lat = [NSString stringWithFormat:@"%c%@", tmp[i], lat];
    }
    
    
    // --- Traitement de la longitude
    NSString * longitude = arrayCoors[3];
    
    char * tmpLongi = [longitude UTF8String];
    
    NSString *minLongi = @"";
    NSString *longi = @"";
    for(int i = longitude.length; i >= 0; i--){
        if(i > longitude.length-7)
            minLongi = [NSString stringWithFormat:@"%c%@", tmpLongi[i], minLongi];
        else
            longi = [NSString stringWithFormat:@"%c%@", tmpLongi[i], longi];
    }
    
    // --- Conversion vers des données compréhensible pour l'application
    float latitudeVal = [minLat floatValue];
    latitudeVal = (latitudeVal/60) + [lat intValue];
    
    float longitudeVal = [minLongi floatValue];
    longitudeVal = (longitudeVal/60)+[longi intValue];
    
    // --- Traitement du signe de la valeur de la latitude & de la longitude (Orientation NESO) ---
    if([arrayCoors[2]  isEqual: @"S"])
        latitudeVal = -latitudeVal;
    
    if([arrayCoors[4] isEqual: @"W"])
        longitudeVal = -longitudeVal;
    
    // --- Crée un pin à la position
    CLLocation *dataCoord = [[CLLocation alloc] initWithLatitude:latitudeVal longitude:longitudeVal];
    [self.points addObject:dataCoord];
    [self pinPosition:dataCoord];
    
    
    
    return coordonees;
    
    
}
// --------- Ajout d'un pointeur sur la map ---------
- (void)pinPosition:(CLLocation *)responseCoordinate {
    
    CLLocationCoordinate2D pinCoordinate;
    pinCoordinate.latitude = responseCoordinate.coordinate.latitude;
    pinCoordinate.longitude = responseCoordinate.coordinate.longitude;
    
    //[self.points addObject:pinCoordinate];
    
    MKPointAnnotation *annotation = [[MKPointAnnotation alloc] init];
    // [annotation setCoordinate: pinCoordinate];
    annotation.coordinate = pinCoordinate;
    annotation.title = @"Waypoint";
    annotation.subtitle = [NSString stringWithFormat:@"Lat: %f - Long: %f", pinCoordinate.latitude, pinCoordinate.longitude];
    [self.mapView addAnnotation:annotation];
    
    
}
- (void)viewWillAppear:(BOOL)animated {
    // --------- Paramétrage du zoom initial : on souhaite avoir une vue de La Rochelle lorsqu'on lance l'application. ---------
    // 1
    CLLocationCoordinate2D zoomLocation;
    zoomLocation.latitude = 46.1474909;
    zoomLocation.longitude= -1.1671439;
    
    // 2
    MKCoordinateRegion viewRegion = MKCoordinateRegionMakeWithDistance(zoomLocation, 0.8	*METERS_PER_MILE, 0.8*METERS_PER_MILE);
    
    // 3
    [_mapView setRegion:viewRegion animated:YES];
    
}

#pragma mark NSURLConnection Delegate Methods

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
    
    _responseData = [[NSMutableData alloc] init];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    
    NSString* newStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    NSString* coordonnes = [self getCoordonnees:newStr];
    [_responseData appendData:data];
    
    
    [self.mapView setDelegate:self];
    
    CLLocationCoordinate2D coordinates[self.points.count];
    
    for(NSInteger index = 0;index<self.points.count;index++){
        
        CLLocation *p =[self.points objectAtIndex:index];
        CLLocationCoordinate2D coordinate;
        
        coordinate.latitude = p.coordinate.latitude ;
        coordinate.longitude = p.coordinate.longitude;
        coordinates[index] = coordinate;
        
    }
    
    
    
}

- (NSCachedURLResponse *)connection:(NSURLConnection *)connection
                  willCacheResponse:(NSCachedURLResponse*)cachedResponse {
    // Renvoie nil pour indiquer qu'il n'est pas nécessaire de stocker une réponse mise en cache pour cette connexion
    return nil;
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    // la demande est complète et les données ont été reçues
    
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    // La demande a échoué
}



@end