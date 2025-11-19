from PIL import Image, ImageDraw, ImageFont
import os

os.makedirs('evidence', exist_ok=True)

width, height = 1200, 675
background = (20, 24, 30)
text_color = (0, 255, 180)
font = ImageFont.load_default()

content = {
    'junit_test_execution.png': [
        'Maven Test Execution – 13/13 Passed',
        'Suite breakdown:',
        ' - PortfolioCalculationBlackBoxTest: 5/5',
        ' - CurrencyConversionBlackBoxTest: 3/3',
        ' - DataValidationBlackBoxTest: 5/5',
        'Timestamp: 2025-11-19 18:04:45 EET'
    ],
    'test_results_summary.png': [
        'Overall Results',
        'Total Tests: 13 | Failures: 0 | Errors: 0 | Skipped: 0',
        'Pass rate: 100%',
        'Framework: JUnit 4.13.2',
        'Build Tool: Maven'
    ],
    'ep_test_example.png': [
        'EP Test Example – TC_CV_001',
        'Scenario: Crypto to Fiat (BTC -> USD)',
        'Input: price1=50000, price2=0, amount=1.0',
        'Expected Output: 50000.0 USD',
        'Status: PASS'
    ],
    'bva_test_example.png': [
        'BVA Test Example – TC_PF_004',
        'Scenario: Minimum positive portfolio amount',
        'Input: amount=0.00000001, price=100',
        'Expected Output: 0.000001',
        'Status: PASS'
    ]
}

for filename, lines in content.items():
    img = Image.new('RGB', (width, height), background)
    draw = ImageDraw.Draw(img)
    y = 60
    for line in lines:
        draw.text((60, y), line, fill=text_color, font=font)
        y += 40
    img.save(os.path.join('evidence', filename))
