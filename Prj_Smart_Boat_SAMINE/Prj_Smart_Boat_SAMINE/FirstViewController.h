//
//  FirstViewController.h
//  Prj_Smart_Boat_SAMINE
//
//  Created by Mohammed Samine on 23/03/2018.
//  Copyright © 2018 Mohammed Samine. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Mapkit/Mapkit.h>

@interface FirstViewController : UIViewController <MKMapViewDelegate,NSURLConnectionDelegate>
{
    
    IBOutlet MKMapView *mapView;
    NSMutableData *_responseData;
    CLLocationCoordinate2D coordinate;
    MKPolylineView *lineView;
    MKPolyline *polyline;
    NSMutableArray *points;
    

    
    
}

@property ( nonatomic) NSMutableArray *points;
@property(nonatomic,retain)IBOutlet MKMapView *mapView;
@property(assign,nonatomic) CLLocationCoordinate2D coordinate;
@property (nonatomic, retain) NSMutableData *responseData;
@property (nonatomic, strong) MKPolylineView *lineView;
@property (nonatomic, strong) MKPolyline *polyline;





@end
