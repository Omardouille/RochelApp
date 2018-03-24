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
    
    
    
    NSMutableData *_responseData;
    
    
}

@property (weak, nonatomic) IBOutlet MKMapView *mapView;
@property ( nonatomic) NSMutableArray *points;
@property (nonatomic, retain) NSMutableData *responseData;







@end
